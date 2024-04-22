const express = require('express');
const mongoose = require('mongoose');
const registrosRouter = require('./routes/registros.js');

const app = express();
const PORT = 3000;

app.use(express.json());

mongoose.connect('mongodb+srv://diegarlin:EAjFZS1nU08KzoBJ@etsiindoor.istf4ds.mongodb.net/TFG')
  .then(() => console.log('Conexión exitosa a MongoDB'))
  .catch(err => console.error('Error al conectar a MongoDB:', err));


app.use('/registros', registrosRouter);

app.get('/', (req, res) => {
    res.send('Bienvenido a la API de Mongo de ETSII INDOOR');
});


app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});