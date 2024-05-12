const express = require('express');
const router = express.Router();
const Registro = require('../models/registro');
const axios = require('axios');
// Obtener todos los registros de entradas y salidas
router.get('/', async (req, res) => {
    try {
        const registros = await Registro.find();
        res.json(registros);
    } catch (err) {
        res.status(500).json({ message: err.message });
    }
});

// Crear un nuevo registro de entrada o salida
router.post('/', async (req, res) => {
    const { beacon, tipo, deviceID } = req.body;
    
    if (!beacon || !tipo || !deviceID) {
        return res.status(400).json({ message: "El identificador del beacon, el tipo y el deviceID son obligatorios." });
    }

    try {
        const habitacion = beacon.slice(-4).toUpperCase().replace(/(\w{2})(\w{2})/, '$1.$2');
        const fechaHora = new Date();
        fechaHora.setHours(fechaHora.getHours() + 2); // Ajustar a la hora de España (GMT+2)
        
        const nuevoRegistro = new Registro({
            beacon: beacon,
            tipo: tipo,
            deviceID: deviceID,
            habitacion: habitacion,
            fechaHora: fechaHora
        });

        const registroGuardado = await nuevoRegistro.save();
        if(tipo === 'entrada'){
            const response = await axios.post('https://api-flask-t5ze.onrender.com/comprobar_sala', {
                room: habitacion,
                deviceID: deviceID
            });
            console.log(response);
            console.log(habitacion);
        }

        res.status(201).json(registroGuardado);
    } catch (err) {
        res.status(400).json({ message: err.message });
    }
});

module.exports = router;