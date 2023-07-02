# DialoGPTAndroidApp

This is an android application that sends prompts and receives responses from a DialoGPT chat model that was fine-tuned on several conversational datasets retrieved from Kaggle. These datasets are:
- [Movie Dialogue Corpus](https://www.kaggle.com/datasets/Cornell-University/movie-dialog-corpus)
- [Chatbot Dataset Topical Chat](https://www.kaggle.com/datasets/arnavsharmaas/chatbot-dataset-topical-chat)
- [Human Conversation Training Data](https://www.kaggle.com/datasets/projjal1/human-conversation-training-data)
- [Conversation Meetings](https://www.kaggle.com/datasets/gogogaurav95/conversation-meetings)
- [Conversation JSON](https://www.kaggle.com/datasets/vaibhavgeek/conversation-json)

## Model Setup Instructions
Use the notebooks in the dialogpt directory of this repo will build and deploy the dialogpt model.
- "Create DialoGPT Training Data.ipynb" - Creates the conversational training dataset to use to fine-tune DialoGPT model
- Train_and_Deploy_DialoGPT.ipynb - Fine-tunes small DialoGPT model and deploys model to a flask API endpoint made accessible to external clients via Ngrok

## Android App Instructions
1. Build and run the Android Studio project
2. Provide the app the deployment url of the endpoint provided by the Flask application
3. Interact with Chatbot

