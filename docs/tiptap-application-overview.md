# TipTap Pay Application Overview

## Introduction

This document provides a comprehensive overview of the TipTap Pay application, focusing on its functionality, user experience, architectural structure, and integration with external systems. The application is designed to provide various store experiences for different organization types, allowing them to purchase TipTap devices and related accessories.

## Project Overview

TipTap Pay is a web application that implements multiple store experiences tailored to different organization types. The application serves as a platform for organizations to purchase TipTap devices, which are used for contactless donations and payments. The application supports various organization types, including:

- General organizations
- Benevity organizations
- Benevity Education organizations
- Benevity Faith-Based organizations
- US Catholic organizations (including specialized flows for dioceses like Fresno)
- Mosques
- Salvation Army US

## User Experience

### Main Workflows

The application implements several key user workflows:

1. **Store Selection**
   - Users start at a central landing page where they can select the appropriate store for their organization type
   - Each store offers a tailored experience specific to the organization's needs

2. **Product Selection and Customization**
   - Users can browse available TipTap devices and accessories
   - Products can be customized based on organization requirements
   - For Benevity stores, users can link their organization to Benevity causes

3. **Shopping Bag Management**
   - Users can add products to their shopping bag
   - They can review, modify quantities, or remove items from their bag

4. **Checkout Process**
   - Multi-step checkout with organization details collection
   - Shipping information collection
   - Payment processing

5. **Account Management**
   - For Benevity stores, users can create and manage accounts
   - Magic link authentication for passwordless login

6. **Order Completion**
   - Success/failure handling
   - Options to continue collecting or start collecting donations

### Entry Points

The application has several entry points:

1. **Main Landing Page** (`home_index.html`)
   - Central hub for accessing all store types
   - Provides navigation to specialized store experiences

2. **Direct Store URLs**
   - Each store can be accessed directly via its URL
   - Organizations may be directed to specific stores based on campaigns or partnerships

3. **My Account Pages**
   - For returning users with existing accounts
   - Accessible via magic links sent to registered email addresses

### Store Types

Each store type offers a specialized experience:

1. **Main Store**
   - General purpose store for standard organizations
   - Basic product selection and checkout flow

2. **Benevity Store**
   - Integration with Benevity causes
   - Organization verification
   - Account management features

3. **Benevity Education Store**
   - Specialized for educational institutions
   - Custom pricing and product bundles for schools

4. **Benevity Faith-Based Store**
   - Tailored for religious organizations
   - Specialized messaging and product offerings

5. **US Catholic Store**
   - Specific to Catholic organizations in the US
   - Includes specialized flows for dioceses (e.g., Fresno Diocese)

6. **Mosques Store**
   - Customized for Islamic religious organizations
   - Specialized messaging and product offerings

7. **Salvation Army US Store**
   - Specific to Salvation Army units in the US
   - Custom product bundles and pricing

8. **Bundles Store**
   - Focused on product bundles
   - May include sales representative interactions

## Application Structure

### Templates Structure

The application uses Thymeleaf templates organized in a hierarchical structure:

1. **Shared Templates and Fragments**
   - `/templates/fragments/` - Reusable UI components
   - `/templates/fragments/navbar.html` - Navigation bar
   - `/templates/fragments/svg_fragments.html` - SVG icons
   - `/templates/fragments/index_fragments.html` - Common UI components

2. **Store-Specific Templates**
   - Each store has its own directory with specialized templates:
     - `/templates/store/` - Main store templates
     - `/templates/benevity/store/` - Benevity store templates
     - `/templates/benevity/education/` - Benevity Education store templates
     - `/templates/benevity/fb/` - Benevity Faith-Based store templates
     - `/templates/us/store/` - US Catholic store templates
     - `/templates/mosques/store/` - Mosques store templates
     - `/templates/saus/store/` - Salvation Army US store templates
     - `/templates/bundles/store/` - Bundles store templates

3. **Common Workflow Templates**
   - Templates for workflows common across stores:
     - Shopping bag templates
     - Checkout templates
     - Response/confirmation templates

4. **Email Templates**
   - `/templates/email/` - Templates for transactional emails
   - `/templates/email/membership_magic_link_email.html` - Magic link authentication emails

The templates use Thymeleaf's template inheritance and fragment inclusion to maintain consistent UI elements across different store experiences while allowing for store-specific customizations.

### Controllers Architecture

The application employs a hybrid controller architecture with both shared and store-specific controllers:

1. **Common Controllers**
   - `HomeController.java` - Manages the main landing page and navigation
   - `BagController.java` - Handles generic shopping bag operations
   - `FormController.java` - Manages form submissions and validation
   - `ShoppingBagController.java` - Additional shopping bag functionality

2. **Store-Specific Controllers**
   - **Benevity Store**
     - `BenevityStoreController.java` - Main controller for Benevity store
     - `BenevityStoreMyAccountController.java` - Handles account-specific operations
     - `BenevityApiController.java` - API endpoints for Benevity integration

   - **Benevity Education Store**
     - `BenevityEducationStoreController.java`
     - `BenevityEducationStoreMyAccountController.java`

   - **Benevity Faith Based Store**
     - `BenevityFBStoreController.java`
     - `BenevityFBStoreMyAccountController.java`

   - **Bundles Store**
     - `BundlesStoreController.java`
     - `BundlesApiController.java`
     - `BundlesStoreWIthSalesRepController.java` - Special handling for sales rep interactions

   - **Mosques Store**
     - `MosquesStoreController.java`

   - **Salvation Army US Store**
     - `SaUsStoreController.java`

   - **US Catholic Store**
     - `UsStoreController.java`
     - `UsStoreFresnoDioceseController.java` - Specialized controller for Fresno Diocese

3. **API Controllers**
   - `MembershipApiController.java` - Handles membership-related API operations
   - Various store-specific API controllers for specialized operations

This architecture follows a domain-driven controller pattern where common functionality is handled by shared controllers, while store-specific logic is encapsulated in dedicated controllers.

### Services Layer

The application uses a service layer to encapsulate business logic and external integrations:

1. **Common Services**
   - `StoreService.java` - Core store functionality
   - `CookieService.java` - Cookie management
   - `FormService.java` - Form processing
   - `PayloadService.java` - Payload handling
   - `StepperService.java` - Multi-step process management
   - `CustomHttpService.java` - HTTP request handling
   - `EmailService.java` - Email sending functionality

2. **Store-Specific Services**
   - `BenevityStoreService.java` - Benevity-specific business logic
   - `BundlesStoreService.java` - Bundles-specific business logic
   - `MosquesStoreService.java` - Mosques-specific business logic
   - `SaUsStoreService.java` - Salvation Army US-specific business logic
   - `UsStoreService.java` - US Catholic-specific business logic

3. **Integration Services**
   - `MembershipService.java` - Handles user membership operations
   - `OrganizationService.java` - Manages organization data
   - `AppService.java` - Application configuration and environment management

4. **Utility Services**
   - `CustomLoggerService.java` - Logging functionality
   - Various utility classes for data formatting, encryption, etc.

The service layer abstracts the business logic from the controllers, promoting code reusability and separation of concerns.

## Athena API Integration

The application integrates with an external system called the Athena API for organization and user management:

### Athena API Usage

1. **Organization/Company Management**
   - The `OrganizationService` uses the Athena API to:
     - Create new companies/organizations via `POST` to `/companies` endpoint
     - Retrieve organization details via `GET` to `/companies/{id}` endpoint

2. **User Management**
   - The `MembershipService` uses the Athena API to:
     - Create and manage user memberships via `/companies/{organizationId}/memberships` endpoint
     - Associate users with organizations

3. **Authentication**
   - Several controllers use the Athena API for authentication:
     - `BenevityStoreMyAccountController`
     - `BenevityFBStoreMyAccountController`
     - `BenevityEducationStoreMyAccountController`
   - These controllers send magic links for passwordless authentication via the `/memberships/send-magic-link` endpoint

4. **Benevity Integration**
   - The `BenevityStoreService` uses the Athena API to:
     - Search for Benevity causes via `/benevity/causes/search` endpoint
     - Retrieve specific cause details via `/benevity/causes/{causeId}` endpoint

### Authentication Mechanism

The application authenticates with the Athena API using an API key:

```java
HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
```

### Environment Configuration

The application has different Athena API configurations for development and production environments, defined in `AppConstants.java`:

```java
public static final class AthenaAppBaseUrl {
    // Different URLs for dev and prod
}
```

## Conclusion

The TipTap Pay application is a sophisticated web application that provides tailored store experiences for different organization types. Its architecture balances shared components with store-specific implementations, allowing for both consistency and specialization. The application integrates with the Athena API for organization and user management, enabling features like organization creation, user membership, and passwordless authentication.

The modular design of the application, with its domain-driven controllers and service layer, makes it maintainable and extensible. New store types can be added by creating new templates, controllers, and services that follow the established patterns, while leveraging the shared components for common functionality.
