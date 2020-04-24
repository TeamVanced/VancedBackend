import express from 'express';
import { database } from '../database';
export const router = express.Router();

//creating a changelog to a version (NOT FINISHED KEEP YOUR HANDS OF THIS CODE YOU FILTHY BASTARD)
router.post('/:id', async (req, res) => {
    const entry = await database.vanced.findOne({ version: req.body.version });
    if (!entry) return res.status(404).json({ message: `Version ${req.body.version} not found!` });
});

router.get('/:id', async (req, res) => {
    const entry = await database.vanced.findOne({ version: req.body.version });
    if (!entry) return res.status(404).json({ message: `Version ${req.body.version} not found!` });

    if (!req.query.app || req.query.app == 'client') {
        res.json({ message: entry.changelogs.client, title: `Vanced ${entry.version}`, buttonpositive: 'Dissmiss', buttonnegative: 'Later' });
    }
    if (req.query.app == 'installer') {
        res.json();
    }
});
