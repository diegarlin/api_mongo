const express = require('express');
const router = express.Router();
const Registro = require('../models/registro');
const mongoose = require('mongoose');

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
    const { beacon, tipo } = req.body;
    
    if (!beacon || !tipo) {
        return res.status(400).json({ message: "El identificador del beacon y el tipo son obligatorios." });
    }

    try {
        const nuevoRegistro = new Registro({
            beacon: beacon,
            tipo: tipo
        });

        const registroGuardado = await nuevoRegistro.save();
        res.status(201).json(registroGuardado);
    } catch (err) {
        res.status(400).json({ message: err.message });
    }
});

module.exports = router;
