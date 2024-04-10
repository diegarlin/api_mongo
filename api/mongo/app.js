const express = require('express');
const mongoose = require('mongoose');
const usuariosRouter = require('./routes/usuarios.js');

const app = express();
const PORT = 3000;

app.use(express.json());

mongoose.connect('mongodb://127.0.0.1:27017/TFG')
  .then(() => console.log('Conexión exitosa a MongoDB'))
  .catch(err => console.error('Error al conectar a MongoDB:', err));


app.use('/usuarios', usuariosRouter);

app.get('/', (req, res) => {
    res.send('Bienvenido a la API de Mongo de ETSII INDOOR');
});


app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});