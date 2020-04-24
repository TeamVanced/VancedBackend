import { connect, connection } from 'mongoose';
import { Request, Response, NextFunction } from 'express';
import { Vanced } from './schemas/Vanced';

connect(process.env.MONGO_STRING || 'mongodb://localhost:27017/api', {
    useCreateIndex: true,
    useNewUrlParser: true,
    useFindAndModify: false,
    useUnifiedTopology: true
});

const db = connection;

db.on('error', err => console.error(err));

db.once('open', () => console.info(`Connected to MongoDB Atlas at ${db.name}!`));

export const database = {
    vanced: Vanced
};

export const getVancedVersion = async (req: Request, res: Response<unknown>, next: NextFunction) => {
    const versionID = req.params.id.replace(/_/g, '.');
    const version = await Vanced.findOne({ version: versionID });
    if (!version) return res.status(404).json({ message: 'Cannot find version, does ' + versionID + ' exist in database?' });

    res.json({ version: version });
    return next();
};
