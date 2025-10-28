from models.product import Product

class ElectronicsProduct(Product):
    def __init__(self, name, stock, price, location, tags=None, warranty_period=None):
        super().__init__(name, stock, price, location, tags)
        self.warranty_period = warranty_period

    def __str__(self):
        tags = ', '.join(self.tags) if self.tags else 'None'
        return (
            f"   Id       : {self.id}\n"
            f"   Name     : {self.name}\n"
            f"   Stock    : {self.stock}\n"
            f"   Price    : ₹{self.price:.2f}\n"
            f"   Location : {self.location}\n"
            f"   Tags     : {tags}\n"
            f"   Warranty Period : {self.warranty_period} years\n"
            "---------------------------"
        )