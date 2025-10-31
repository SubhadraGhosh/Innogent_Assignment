from prisma import Prisma

# Create a Prisma client instance to interact with the database
prisma = Prisma()

#Genrate database connection
async def get_db():
    if not prisma.is_connected():
        await prisma.connect()
    return prisma