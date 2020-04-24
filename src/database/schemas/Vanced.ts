import { Document, Schema, model } from 'mongoose';
import marked from 'marked';
import { sanitize } from 'dompurify';

export interface Vanced extends Document {
    version: string;
    createdAt: number;
    changelogs: {
        origin: string;
        html: string;
        client: string;
    };
}

const VancedSchema = new Schema({
    version: String,
    createdAt: Number,
    changelogs: {
        origin: String,
        html: String,
        client: String
    }
});

VancedSchema.pre('validate', function (next) {
    const self = this as Vanced;
    self.changelogs.html = sanitize(marked(self.changelogs.origin));
    if (!self.createdAt) self.createdAt = Date.now();

    next();
});

export const Vanced = model<Vanced>('VancedApi', VancedSchema);
