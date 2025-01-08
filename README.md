# Coupons-Management

## Implemented Cases
### 1. Cart Management
- Add Item: Add a new product to the cart. If the product already exists, its quantity is updated.
- Remove Item: Remove a product from the cart by its productId.
- Update Quantity: Modify the quantity of a specific item in the cart. If the quantity is set to 0, the item is removed.
- Clear Cart: Remove all items, reset the cart totals, and clear the applied coupon.
### 2. Price Calculations
- Total Price: Calculate the sum of all items in the cart (before discounts).
- Final Price: Calculate the total price after applying discounts.
- Per-Item Discount: Each item in the cart can have its own discount applied.
### 3. Coupon Management
- Apply a coupon to the cart to adjust the totalDiscount and recalculate the finalPrice.


## Assumptions
### 1. Default Discounts
 - If no discount is specified, it defaults to 0.0.
### 2. Coupon Application
- Only one coupon can be applied to a cart at a time.
### 3. No User-Defined Limits
- Users can add as many items as they want without constraints on total price or item quantity.
### 4. Standalone System
- The current implementation assumes no integration with external services like inventory, payment gateways, or tax calculators.


### API List
1. Coupon Management APIs : `Endpoint: POST /coupon/create . Description: Creates a new coupon and stores it in the system.`

2. Retrieve All Coupons : `Endpoint: GET /coupon . Description: Fetches a list of all available coupons.`

3. Retrieve Coupon by ID : `Endpoint: GET /coupon/{id}`

4. Update Coupon : `Endpoint: PUT /coupon/{id}`

5. Delete Coupon : `Endpoint: DELETE /coupon/{id}`

6. Get Applicable Coupons : `Endpoint: POST /coupon/applicable-coupons`

7. Apply Coupon : `Endpoint: POST /coupon/apply`


### Strategy Pattern Usage
- Context: Discount Calculation
- Different discount types require specific logic for calculating the discount amount (e.g., percentage-based, flat discounts, or item-specific discounts).
- The Strategy Pattern enables flexible and extensible implementation for multiple discount strategies.

### Advantages of Strategy Pattern
- Extensibility: New discount types can be added without modifying existing code.
- Flexibility: Logic for each discount type is encapsulated in its own class.
- Separation of Concerns: Simplifies code in the service layer by delegating discount logic to strategies.
