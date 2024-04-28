require('dotenv').config();
const express = require('express');
const mongoose = require('mongoose');
const registrosRouter = require('./routes/registros.js');
const habitacionesRouter = require('./routes/habitaciones.js');


const app = express();
const PORT = process.env.PORT

app.use(express.json());

mongoose.connect(process.env.MONGODB_CONNECT_URI)
  .then(() => console.log('Conexión exitosa a MongoDB'))
  .catch(err => console.error('Error al conectar a MongoDB:', err));


app.use('/registros', registrosRouter);
app.use('/habitaciones', habitacionesRouter);

app.get('/', (req, res) => {
    res.send('Bienvenido a la API de Mongo de ETSII INDOOR');
});


app.listen(PORT, () => {
    console.log(`Servidor corriendo en http://localhost:${PORT}`);
});