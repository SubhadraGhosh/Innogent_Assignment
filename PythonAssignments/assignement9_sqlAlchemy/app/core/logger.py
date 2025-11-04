import logging


logging.basicConfig(
    level=logging.INFO,  # You can use DEBUG, WARNING, ERROR as needed
    format="%(asctime)s - %(levelname)s - %(message)s",
    handlers=[
        logging.FileHandler("app.log"),   # Logs will be saved in 'app.log'
        logging.StreamHandler()           # Also show logs in the terminal
    ]
)

logger = logging.getLogger(__name__)
