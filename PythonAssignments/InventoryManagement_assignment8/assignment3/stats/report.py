import numpy as np

def show_stats(products):
    try:
        if not products:
            print("No products available for statistics.")
            return

        # Extract numerical data
        prices = np.array([p.price for p in products])
        stocks = np.array([p.stock for p in products])
        values = prices * stocks  # total value per product

        print("\nINVENTORY STATISTICS REPORT")
        print(f"Average Price of Items          : ₹{np.mean(prices):.2f}")
        print(f"Most Expensive Item Price       : ₹{np.max(prices):.2f}")
        print(f"Total Count of All Items in Stock: {np.sum(stocks)}")
        print(f"Total Inventory Value (All)     : ₹{np.sum(values):.2f}")

        print("\nTotal Value per Product:")
        for p, val in zip(products, values):
            print(f"  {p.name} ₹{val:.2f}")

        # Tag-specific stats
        tag = input("\nEnter a tag to filter (e.g., 'clearance'): ").strip()
        if tag:
            tagged = [p for p in products if tag in p.tags]
            if tagged:
                t_prices = np.array([p.price for p in tagged])
                t_values = np.array([p.price * p.stock for p in tagged])
                print(f"\nStats for tag '{tag}':")
                print(f"Average Price: ₹{np.mean(t_prices):.2f}")
                print(f"Total Value  : ₹{np.sum(t_values):.2f}")
            else:
                print(f"No products found with tag '{tag}'.")
    except Exception as e:
        print(f"Error generating statistics: {e}")