import joblib
import os

# Load saved models
model_path = os.path.join(os.getcwd(), "models/model.pkl")
vectorizer_path = os.path.join(os.getcwd(), "models/vectorizer.pkl")
label_encoder_path = os.path.join(os.getcwd(), "models/label_encoder.pkl")

model = joblib.load(model_path)
vectorizer = joblib.load(vectorizer_path)
label_encoder = joblib.load(label_encoder_path)

def predict_intent(text):
    # Convert input text to TF-IDF vector
    text_vector = vectorizer.transform([text])

    # Predict intent
    intent_index = model.predict(text_vector)[0]

    # Convert label index to actual intent
    intent = label_encoder.inverse_transform([intent_index])[0]
    return intent
