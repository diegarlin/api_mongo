const express = require('express');
const router = express.Router();
const Registro = require('../models/registro');
const moment = require('moment');


// Obtener el número de personas que ha pasado/entrado por cada habitación en la historia que comienza con una letra específica
router.get('/entradas_por_habitacion', async (req, res) => {
    try {
        // Obtiene la letra desde req.query
        const { letra } = req.query;

        // Crea una expresión regular para buscar habitaciones que comiencen con el parámetro proporcionado
        // Si no se proporciona ninguna letra, la expresión regular coincidirá con cualquier habitación
        const roomRegex = letra ? new RegExp(`^${letra}`, 'i') : /.*/;

        // Obtén una lista de todas las habitaciones que coinciden con la expresión regular
        const rooms = await Registro.find({ habitacion: { $regex: roomRegex } }).distinct('habitacion');

        // Para cada habitación, cuenta el número de 'entrada' registros
        const counts = await Promise.all(rooms.map(async (room) => {
            const entradaCount = await Registro.countDocuments({ habitacion: room, tipo: 'entrada' });

            // Devuelve un objeto con el nombre de la habitación y el número de 'entrada' registros
            return {
                habitacion: room,
                numPersonas: entradaCount
            };
        }));

        // Devuelve la lista de conteos
        res.json(counts);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});

// Obtener el número de personas que han pasado/entrado por cada habitación entre fecha inicio y fecha fin
router.get('/entradas_por_habitacion_fecha', async (req, res) => {
    try {
        const { habitacion, fechaInicio, fechaFin } = req.query;

        if (!fechaInicio || !fechaFin) {
            return res.status(400).json({ message: "Los parámetros fechaInicio y fechaFin son obligatorios" });
        }

        // Convertir las fechas a objetos Date teniendo en cuenta la zona horaria GMT+2
        const fechaInicioDate = moment.utc(fechaInicio, "YYYY-MM-DD HH:mm").utcOffset("+02:00").toDate();
        const fechaFinDate = moment.utc(fechaFin, "YYYY-MM-DD HH:mm").utcOffset("+02:00").toDate();
        console.log(fechaInicioDate, fechaFinDate)
        // Crear el filtro para la consulta
        let filtro = {
            tipo: 'entrada',
            fechaHora: { $gte: fechaInicioDate, $lte: fechaFinDate }
        };

        if (habitacion) {
            filtro.habitacion = new RegExp('^' + habitacion, 'i'); // Filtrar por habitación, ignorando mayúsculas y minúsculas
        }

        // Obtener todos los registros de entrada en el intervalo de tiempo especificado
        const registrosEntrada = await Registro.find(filtro).lean();

        // Crear un objeto para almacenar el recuento de entradas en cada habitación
        const habitaciones = {};

        // Recorrer los registros de entrada y actualizar el recuento para cada habitación
        registrosEntrada.forEach(registro => {
            const habitacion = registro.habitacion;
            if (habitaciones[habitacion]) {
                habitaciones[habitacion]++;
            } else {
                habitaciones[habitacion] = 1;
            }
        });

        // Convertir el objeto habitaciones en una lista de objetos
        const listaHabitaciones = Object.keys(habitaciones).map(habitacion => ({
            habitacion: habitacion,
            numPersonas: habitaciones[habitacion]
        }));

        res.json(listaHabitaciones);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});

router.get('/personas_actual_habitaciones', async (req, res) => {
    try {
        // Crea una expresión regular para buscar habitaciones que comiencen con el parámetro proporcionado
        const { letra } = req.query;

        // Crea una expresión regular para buscar habitaciones que comiencen con el parámetro proporcionado
        // Si no se proporciona ninguna letra, la expresión regular coincidirá con cualquier habitación
        const roomRegex = letra ? new RegExp(`^${letra}`, 'i') : /.*/;

        // Obtén una lista de todas las habitaciones que coinciden con la expresión regular
        const rooms = await Registro.find({ habitacion: { $regex: roomRegex } }).distinct('habitacion');

        // Para cada habitación, cuenta el número de personas en la habitación
        const counts = await Promise.all(rooms.map(async (room) => {
            const entradaRegistros = await Registro.find({ habitacion: room, tipo: 'entrada' }).lean();
            const peopleInRoom = await Promise.all(entradaRegistros.map(async (entrada) => {
                 // Para cada registro de 'entrada', busca un registro de 'salida' correspondiente
                // Un registro de 'salida' correspondiente es uno que tiene el mismo deviceID y habitación,
                // y una fechaHora mayor que la del registro de 'entrada'

                const salidaRegistro = await Registro.findOne({ habitacion: room, tipo: 'salida', deviceID: entrada.deviceID, fechaHora: { $gt: entrada.fechaHora } }).lean();

                // Si no hay un registro de 'salida' correspondiente, la persona todavía está en la habitación
                // Por lo tanto, devuelve true si no hay un registro de 'salida' correspondiente, y false de lo contrario
                return !salidaRegistro;
            }));

            // Devuelve un objeto con el nombre de la habitación y el número de personas en esa habitación
            return {
                habitacion: room,
                numPersonas: peopleInRoom.filter(Boolean).length
            };
        }));

        // Devuelve la lista de conteos
        res.json(counts);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});


router.get('/personas_actual_fecha', async (req, res) => {
    try {
        // Obtiene la fecha y la habitación desde req.query
        const { fecha, habitacion } = req.query;

        // Crea una expresión regular para buscar habitaciones que comiencen con el parámetro proporcionado
        // Si no se proporciona ninguna habitacion, la expresión regular coincidirá con cualquier habitación
        const roomRegex = habitacion ? new RegExp(`^${habitacion}`, 'i') : /.*/;

        // Convierte la fecha proporcionada a un objeto Date
        const fechaDate = moment.utc(fecha, "YYYY-MM-DD HH:mm").utcOffset("+02:00").toDate();

        // Obtén una lista de todas las habitaciones que coinciden con la expresión regular
        const rooms = await Registro.find({ habitacion: { $regex: roomRegex } }).distinct('habitacion');

        // Para cada habitación, cuenta el número de personas en la habitación en la fecha proporcionada
        const counts = await Promise.all(rooms.map(async (room) => {
            const entradaRegistros = await Registro.find({ habitacion: room, tipo: 'entrada', fechaHora: { $lte: fechaDate } }).lean();
            const peopleInRoom = await Promise.all(entradaRegistros.map(async (entrada) => {
                const salidaRegistro = await Registro.findOne({ habitacion: room, tipo: 'salida', deviceID: entrada.deviceID, fechaHora: { $gt: entrada.fechaHora, $lte: fechaDate } }).lean();
                return !salidaRegistro;
            }));

            // Devuelve un objeto con el nombre de la habitación y el número de personas en esa habitación
            return {
                habitacion: room,
                numPersonas: peopleInRoom.filter(Boolean).length
            };
        }));

        // Devuelve la lista de conteos
        res.json(counts);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});

module.exports = router;


