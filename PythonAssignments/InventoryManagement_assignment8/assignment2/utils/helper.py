from models.product import Product
from models.food_product import FoodProduct
from models.electronics_product import ElectronicsProduct

def input_tags():
    tags_input = input("Enter product tags (comma-separated): ")
    return {tag.strip() for tag in tags_input.split(',')} if tags_input else set()

def input_product():
    name = input("Enter product name: ")
    stock = int(input("Enter stock: "))
    price = float(input("Enter price: "))
    location = input("Enter location: ")
    tags = input_tags()
    return Product(name, stock, price, location, tags)

def input_food_product():
    name = input("Enter product name: ")
    stock = int(input("Enter stock: "))
    price = float(input("Enter price: "))
    location = input("Enter location: ")
    tags = input_tags()
    expiration_date = input("Enter expiration date (YYYY-MM-DD): ")
    return FoodProduct(name, stock, price, location, tags, expiration_date)

def input_electronics_product():
    name = input("Enter product name: ")
    stock = int(input("Enter stock: "))
    price = float(input("Enter price: "))
    location = input("Enter location: ")
    tags = input_tags()
    warranty_period = input("Enter warranty period (in years): ")

    return ElectronicsProduct(name, stock, price, location, tags, warranty_period)


#Constant for low stock threshold
def LOW_STOCK_THRESHOLD():
    return 5