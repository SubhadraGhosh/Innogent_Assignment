import itertools

class Product:
    _id_counter = itertools.count(1)
    def __init__(self, name, stock, price, location, tags=None):
        self.id = next(Product._id_counter)
        self.name = name
        self.stock = stock
        self.price = price
        self.location = location
        self.tags = set(tags) if tags else set()

    def is_low_stock(self):
        return self.stock < 5

    def apply_discount(self, percent):
        self.price *= (1 - percent / 100)

    def __str__(self):
        tags = ', '.join(self.tags) if self.tags else 'None'
        return (
            f"   Id       : {self.id}\n"
            f"   Name     : {self.name}\n"
            f"   Stock    : {self.stock}\n"
            f"   Price    : ₹{self.price:.2f}\n"
            f"   Location : {self.location}\n"
            f"   Tags     : {tags}\n"
            f""
            "---------------------------"
        )


    def set_id(self):
        self.id = Product.id_generator
        Product.id_generator += 1