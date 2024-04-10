// models/usuario.js
const mongoose = require('mongoose');
const usuarioSchema = new mongoose.Schema({
    nombre: String,
    email: String,
    edad: Number
});

module.exports = mongoose.model('Usuario', usuarioSchema);
