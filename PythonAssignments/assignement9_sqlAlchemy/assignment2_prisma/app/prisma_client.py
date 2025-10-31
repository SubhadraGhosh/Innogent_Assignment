
from prisma import Prisma

db = Prisma()

async def get_db():
    return await db.connect()

