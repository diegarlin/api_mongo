const mongoose = require('mongoose');

const registroSchema = new mongoose.Schema({
    beacon: {
        type: String, // Cambiado a String
        ref: 'Beacon' // Referencia al modelo de Beacon
    },
    tipo: {
        type: String,
        enum: ['entrada', 'salida'] // Tipo de interacción (entrada/salida)
    },
    fechaHora: {
        type: Date,
        default: Date.now // Fecha y hora de la interacción
    }
});

module.exports = mongoose.model('Registro', registroSchema);
