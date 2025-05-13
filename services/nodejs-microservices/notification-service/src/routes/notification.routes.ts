import { Router } from "express";

const router = Router();

// GET /api/notifications
router.get("/", async (req, res) => {
  res.status(200).json({ message: "This will list stored notifications soon!" });
});

export default router;
