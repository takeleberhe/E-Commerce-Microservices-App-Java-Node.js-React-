import mongoose from "mongoose";

const NotificationSchema = new mongoose.Schema({
  type: String,
  data: Object,
  createdAt: {
    type: Date,
    default: Date.now,
  },
});

export default mongoose.model("Notification", NotificationSchema);
