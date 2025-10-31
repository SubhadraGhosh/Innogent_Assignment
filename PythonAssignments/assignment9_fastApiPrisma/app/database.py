
from prisma import Prisma

db = Prisma()


async def get_db():
    try:
        return await db.connect()
    except Exception as e:
        print(e)

async def connect_db():
    try:
        return await db.connect()
    except Exception as e:
        print(e)

async def disconnect_db():
    try:
        await db.disconnect()
    except Exception as e:
        print(e)
