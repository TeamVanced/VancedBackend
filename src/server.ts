import express from 'express';

// app.get('/api/v1/site/edit', async (req, res) => {
//     const articles = await Article.find().sort({ createdAt: 'desc' })
//     res.render('api/index', { articles: articles })
//   })
const app = express();

app.use(express.json());

// app.use('/', require('./website/index'))
app.use('/api/v1/versions/', require('./api/vancedversions'));
app.use('/api/v1/changelog/', require('./api/vancedchangelog'));

const port = 4000;
app.listen(port, () => console.log('Server Started on port' + port));
