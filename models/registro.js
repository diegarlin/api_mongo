const mongoose = require('mongoose');

const registroSchema = new mongoose.Schema({
    beacon: {
        type: String, // Cambiado a String
    },
    habitacion: {
        type: String, 
    },
    tipo: {
        type: String,
        enum: ['entrada', 'salida'] // Tipo de interacción (entrada/salida)
    },
    fechaHora: {
        type: Date,
        default: Date.now // Fecha y hora de la interacción
    },
    deviceID: {
        type: String
    }
});

module.exports = mongoose.model('Registro', registroSchema);
