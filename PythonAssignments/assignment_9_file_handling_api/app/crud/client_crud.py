from idlelib.iomenu import errors

from typing import List

from app.schemas.schema import ClientRequest
from app.schemas.schema import ClientResponse
from app.core.db import prisma

#Inserting single client
async def insert_client(client: ClientRequest):
    return await prisma.client.create(data=client.dict())

#Inserting multiple clients coming from csv/excel
async def insert_bulk_client(clients: List[ClientRequest]):
    created, errors = [], []

    for client in clients:
        try:
            record = await insert_client(client)
            created.append(record)
        except Exception as e:
            errors.append({"email": client.email, "error": str(e)})

    return {"created": created, "errors": errors}

# For Download/Export
async def get_clients_for_download(skip=0, limit=100):
    return await prisma.client.find_many(skip=skip, take=limit)