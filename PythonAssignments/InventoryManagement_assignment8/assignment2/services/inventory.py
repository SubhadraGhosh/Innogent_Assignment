from models.product import Product

class Inventory:
    def __init__(self):
        self.products = []

    #Listing all the products
    def list_products(self):
        if self.products:
            for p in self.products:
                print(p)
        else:
            print("No products in inventory.")

    #Low on stock warnings. (LOW_STOCK = 5)
    def low_stack_warning(self):
        flag = False
        for p in self.products:
            if p.is_low_stock():
                print(f"Warning: Low stock for product '{p.name}'. Only {p.stock} left.")
                flag = True
        if not flag:
            print("All products are sufficiently stocked.")

    #Add product.
    def add_product(self, product):
        self.products.append(product)
        print(f"Product '{product.name}' added successfully.")

    #Update stock.
    def update_stock(self, id, new_stock):
        for p in self.products:
            if p.id == id:
                p.stock = new_stock
                print(f"Stock for product '{p.name}' updated to {new_stock}.")
                return
        print(f"Product '{p.id}' not found in inventory.")

    #Delete product
    def delete_product(self, id):
        for p in self.products:
            if p.id == id:
                self.products.remove(p)
                print(f"Product '{p.name}' deleted successfully.")
                return
        print(f"Product with ID '{id}' not found in inventory.")

    #Print Total value of all products in stock.
    def total_inventory_value(self):
        total_value = sum(p.price * p.stock for p in self.products)
        print(f"Total inventory value: ₹{total_value:.2f}")
        return total_value

    #Applying discount
    def apply_discount(self, tags, percent=0.5):
        for p in self.products:
            if ('clearance' in p.tags) and (tags & p.tags):  # check for matching tags
                original_price = p.price
                p.price *= (1 - percent)
                print(f"Discounted '{p.name}' from ₹{original_price:.2f} to ₹{p.price:.2f}")