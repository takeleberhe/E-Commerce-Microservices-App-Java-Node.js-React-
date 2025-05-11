import express, { Application } from 'express';
import cors from 'cors';
import bodyParser from 'body-parser';
import routes from './routes';

const app: Application = express();
const PORT = process.env.PORT || 3001;

app.use(cors());
app.use(bodyParser.json());
app.use('/api', routes);

app.listen(PORT, () => {
    console.log(`Notification service running on port ${PORT}`);
});
