import os
from dotenv import load_dotenv
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker, declarative_base
from app.core.logger import logger  # import your logger

load_dotenv()

DATABASE_URL = os.getenv("DATABASE_URL")
if not DATABASE_URL:
    logger.critical("DATABASE_URL not set in environment variables.")
    raise RuntimeError("DATABASE_URL not set in environment")

try:
    # Create engine for MySQL
    engine = create_engine(DATABASE_URL, echo=False, pool_pre_ping=True)
    logger.info("Database engine created successfully.")
except Exception as e:
    logger.exception(f"Error creating database engine: {e}")
    raise

SessionLocal = sessionmaker(autocommit=False, autoflush=False, bind=engine)
Base = declarative_base()


def init_db():
    """Initialize database and create tables."""
    try:
        Base.metadata.create_all(bind=engine)
        logger.info("Database tables created successfully.")
    except Exception as e:
        logger.exception(f"Error during database initialization: {e}")
        raise


def get_db():
    """Provide a database session."""
    db = SessionLocal()
    try:
        logger.debug("New database session created.")
        yield db
    except Exception as e:
        logger.exception(f"Error during DB session usage: {e}")
        raise
    finally:
        db.close()
        logger.debug("Database session closed.")
