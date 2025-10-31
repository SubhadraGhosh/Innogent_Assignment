from fastapi import APIRouter, UploadFile, File, HTTPException
import pandas as pd
import io
from app.schemas.schema import ClientRequest
from app.crud.client_crud import insert_bulk_client

router = APIRouter()

@router.post("/upload-clients")
async def upload_clients(file: UploadFile = File(...)):
    if not file.filename.endswith('.csv'):
        raise HTTPException(status_code=400, detail="Only CSV files allowed!")

    contents = await file.read()
    try:
        df = pd.read_csv(io.BytesIO(contents))
    except Exception:
        raise HTTPException(status_code=400, detail="Invalid CSV format!")

    clients = []
    for _, row in df.iterrows():
        clients.append(ClientRequest(
            name=row['name'],
            email=row['email'],
            about=row.get('about', None)
        ))

    result = await insert_bulk_client(clients)
    return result
