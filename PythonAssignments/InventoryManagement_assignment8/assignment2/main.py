from utils.helper import input_product, input_food_product, input_electronics_product
from services.inventory import Inventory
from models.product import Product
from models.food_product import FoodProduct
from models.electronics_product import ElectronicsProduct


def main():
    inventory = Inventory()
    inventory.products = [
        FoodProduct("Amul Milk", 20, 50.0, "Shelf-1", {"dairy", "fresh"}, "2024-12-01"),
        Product("Mango", 15, 30.0, "Shelf-2", {"fruit", "seasonal", "clearance"}),
        Product("Maggi", 50, 12.0, "Shelf-3", {"grocery", "instant"}),
        Product("Britannia Biscuits", 40, 25.0, "Shelf-4", {"snack", "grocery", "clearance"}),
        Product("Tata Tea", 30, 150.0, "Shelf-5", {"beverage", "grocery"}),
        Product("Spinach", 10, 20.0, "Shelf-1", {"vegetable", "fresh"}),
        Product("Parle-G", 60, 5.0, "Shelf-4", {"snack", "grocery"}),
        Product("Dettol Soap", 25, 40.0, "Shelf-6", {"hygiene", "essential", "clearance"}),
        Product("Coca-Cola", 45, 60.0, "Shelf-5", {"beverage", "refreshing"}), ]

    # User Input Loop
    while True:
        choice = input("""
        Inventory Management System
        1. List all products
        2. Low stock warnings
        3. Add a new product
        4. Update product stock
        5. Delete a product
        6. Total inventory value
        7. Apply discount to products by tags
        8. Exit

        Enter your choice: """)

        if choice == '1':
            inventory.list_products()
        elif choice == '2':
            inventory.low_stack_warning()
        elif choice == '3':
            choice_type = int(input(
                "Select product type to add:\n1. General Product\n2. Food Product\n3. Electronics Product\nEnter choice: "))
            if choice_type == 1:
                product = input_product()
            elif choice_type == 2:
                product = input_food_product()
            elif choice_type == 3:
                product = input_electronics_product()
            inventory.add_product(product)
        elif choice == '4':
            id = int(input("Enter product ID to update stock: "))
            new_stock = int(input("Enter new stock value: "))
            inventory.update_stock(id, new_stock)
        elif choice == '5':
            id = int(input("Enter product ID to delete: "))
            inventory.delete_product(id)
        elif choice == '6':
            inventory.total_inventory_value()
        elif choice == '7':
            tags_input = input("Enter tags for discount (comma-separated): ")
            tags = {tag.strip() for tag in tags_input.split(',')} if tags_input else set()
            percent = float(input("Enter discount percentage (default 50%): ") or 50)
            inventory.apply_discount(tags, percent / 100)
        elif choice == '8':
            print("Exiting Inventory Management System.")
            break
        else:
            print("Invalid choice. Please try again.")

if __name__ == "__main__":
    main()