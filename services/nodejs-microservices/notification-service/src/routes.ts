import { Router, Request, Response } from 'express';

const router = Router();

router.get('/notifications', (req: Request, res: Response) => {
    res.json({ message: 'List of notifications' });
});

router.post('/notifications', (req: Request, res: Response) => {
    const { message, recipient } = req.body;
    res.json({ message: `Notification sent to ${recipient}`, content: message });
});

export default router;
