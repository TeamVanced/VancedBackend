import express from 'express';
import { database } from '../database';

export const router = express.Router();

//creating a version
router.post('/', async (req, res) => {
    const version = await database.vanced.create({ version: req.body.version });
    res.status(201).json(version);
});

// Getting all versions from latest to oldest
router.get('/', async (_req, res) => {
    res.json({ versions: (await database.vanced.find()).sort((x, y) => x.createdAt + y.createdAt).map(ele => ele.version) });
});

// Deleting version
router.delete('/:id', async (req, res) => {
    const entry = await database.vanced.findOneAndDelete({ version: req.body.version });
    if (entry) res.json({ message: `Successfully deleted version ${entry.version}` });
    else res.status(404).json({ message: `Unable to find version ${req.body.version}` });
});
