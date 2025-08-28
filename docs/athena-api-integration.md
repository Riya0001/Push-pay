# Athena API Integration Documentation

This document provides a comprehensive overview of the Athena API integrations used in the orderforms-netsuite-injection project. The application integrates with Athena for various functionalities including organization management, user management, campaign management, and Benevity integrations.

## Application Overview

The TipTap Pay application is a web application that implements multiple store experiences tailored to different organization types. The application serves as a platform for organizations to purchase TipTap devices, which are used for contactless donations and payments. The application supports various store types, including:

- General organizations
- Benevity organizations (including Education and Faith-Based variants)
- US Catholic organizations
- Mosques
- Salvation Army US
- Bundles Store

Each store type offers a specialized experience with custom product offerings, pricing, and workflows.

## Configuration

### Base URLs

The application uses different base URLs for development and production environments:

```java
public static final class AthenaAppBaseUrl {
    public static final String PRODUCTION = "https://app.tiptappay.ca";
    public static final String DEVELOPMENT = "https://app-dev.tiptappay.ca";
}
```

### API Configuration

The Athena API configuration is defined in the application properties:

```yaml
athena:
  api:
    prefix: api/athena/v1
    key: ${ATHENA_INTERNAL_API_KEY}
```

### Authentication

All requests to the Athena API are authenticated using an API key:

```java
Map<String, String> headers = Map.of(
    HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
    HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
);
```

## API Endpoints

### 1. Organization/Company Management

#### Service Location
`src/main/java/com/tiptappay/store/app/service/organization/OrganizationService.java`

#### Endpoints

| Operation | Endpoint | Method | Description |
|-----------|----------|--------|-------------|
| Fetch All Organizations | `/api/athena/v1/companies` | GET | Retrieves a list of all organizations |
| Fetch Organization by ID | `/api/athena/v1/companies/{id}` | GET | Retrieves details of a specific organization |
| Create Organization | `/api/athena/v1/companies` | POST | Creates a new organization |

#### Example Usage

```java
// Fetch all organizations
public List<Organization> fetchOrganizations() {
    String urlPath = "/" + app.getAthenaApiPrefix() + "/companies";
    Map<String, String> headers = Map.of(
            HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
            HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
    );
    CustomHttpResponse customHttpResponse = customHttpService.getRequest(app.getBaseUrl() + urlPath, headers);
    // Process response...
}

// Create organization
public OrganizationResponse postOrganization(OrganizationRequest organizationRequest) {
    String urlPath = "/" + app.getAthenaApiPrefix() + "/companies";
    Map<String, String> headers = Map.of(
            HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
            HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
    );
    String requestBody = DataUtils.convertToJsonString(organizationRequest);
    CustomHttpResponse customHttpResponse = customHttpService.postRequest(app.getBaseUrl() + urlPath, headers, requestBody);
    // Process response...
}
```

### 2. User Management (Memberships)

#### Service Location
`src/main/java/com/tiptappay/store/app/service/membership/MembershipService.java`

#### Endpoints

| Operation | Endpoint | Method | Description |
|-----------|----------|--------|-------------|
| Fetch Memberships by Organization | `/api/athena/v1/companies/{organizationId}/memberships` | GET | Retrieves all memberships for an organization |
| Create Membership | `/api/athena/v1/companies/{organizationId}/memberships` | POST | Creates a new membership for an organization |
| Send Magic Link | `/api/athena/v1/memberships/send-magic-link` | POST | Sends a magic link for passwordless authentication |

#### Example Usage

```java
// Fetch memberships by organization ID
public List<Membership> fetchMembershipsByOrganizationId(int organizationId) {
    String urlPath = "/" + app.getAthenaApiPrefix() + "/companies/" + organizationId + "/memberships";
    Map<String, String> headers = Map.of(
            HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
            HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
    );
    CustomHttpResponse customHttpResponse = customHttpService.getRequest(app.getBaseUrl() + urlPath, headers);
    // Process response...
}

// Create membership
public CustomHttpResponse postMembership(int organizationId, MembershipRequest membershipRequest) {
    String urlPath = "/" + app.getAthenaApiPrefix() + "/companies/" + organizationId + "/memberships";
    Map<String, String> headers = Map.of(
            HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
            HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
    );
    String requestBody = DataUtils.convertToJsonString(membershipRequest);
    return customHttpService.postRequest(app.getBaseUrl() + urlPath, headers, requestBody);
}
```

### 3. Campaign Management

#### Service Locations
- `src/main/java/com/tiptappay/store/app/service/CampaignService.java`
- `src/main/java/com/tiptappay/store/app/service/StoreService.java`

#### Endpoints

| Operation | Endpoint | Method | Description |
|-----------|----------|--------|-------------|
| Fetch Campaigns by Company | `/api/athena/v1/companies/{id}/campaigns` | GET | Retrieves all campaigns for a specific company |
| Create Campaign | `/api/athena/v1/campaigns` | POST | Creates a new campaign |

#### Example Usage

```java
// Fetch campaigns for a company
public List<Campaign> fetchCampaigns() {
    String getUrl = appService.getBaseUrl() + "/" + appService.getAthenaApiPrefix() + "/companies/313/campaigns";
    Map<String, String> headers = Map.of(
            HTTP_HEADER_NAME_ACCEPT, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
            HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
            HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + appService.getAthenaApiKey()
    );
    CustomHttpResponse getResponse = customHttpService.getRequest(getUrl, headers);
    // Process response...
}

// Create campaign
public CustomHttpResponse createAthenaCampaign(ResponsePayment responsePayment) {
    String url = appService.getBaseUrl() + "/" + appService.getAthenaApiPrefix() + "/campaigns";
    Map<String, String> headers = Map.of(
            HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
            HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + appService.getAthenaApiKey()
    );
    Map<String, Object> campaignPayload = new HashMap<>();
    campaignPayload.put("campaignName", responsePayment.getSoNumber());
    campaignPayload.put("customerId", responsePayment.getCustomerId());
    campaignPayload.put("orderId", responsePayment.getId());
    campaignPayload.put("description", "Order " + responsePayment.getSoNumber() + " created via Canada Store");
    campaignPayload.put("source", "canada-store");
    campaignPayload.put("status", "active");

    String jsonBody = DataUtils.convertToJsonString(campaignPayload);
    return customHttpService.postRequest(url, headers, jsonBody);
}
```

### 4. Benevity Integration

#### Service Location
`src/main/java/com/tiptappay/store/app/service/benevity/BenevityStoreService.java`

#### Endpoints

| Operation | Endpoint | Method | Description |
|-----------|----------|--------|-------------|
| Get Benevity Cause | `/api/athena/v1/benevity/causes/{causeId}` | GET | Retrieves details of a specific Benevity cause |
| Search Benevity Causes | `/api/athena/v1/benevity/causes/search?search_term={term}` | GET | Searches for Benevity causes |

#### Example Usage

```java
// Get a specific Benevity cause
public BenevityCause getBenevityCause(String causeId) {
    String encodedCauseId = URLEncoder.encode(causeId, StandardCharsets.UTF_8);
    String urlPath = "/" + app.getAthenaApiPrefix() + "/benevity/causes/" + encodedCauseId;
    Map<String, String> headers = Map.of(
            HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
            HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
    );
    CustomHttpResponse customHttpResponse = customHttpService.getRequest(app.getBaseUrl() + urlPath, headers);
    return processSingleCause(customHttpResponse.getResponseBody());
}

// Search for Benevity causes
public String searchBenevityCauses(String searchTerm) {
    String encodedSearchTerm = URLEncoder.encode(searchTerm, StandardCharsets.UTF_8);
    String urlPath = "/" + app.getAthenaApiPrefix() + "/benevity/causes/search?search_term=" + encodedSearchTerm + "&include_children=false";
    Map<String, String> headers = Map.of(
            HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
            HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + app.getAthenaApiKey()
    );
    CustomHttpResponse customHttpResponse = customHttpService.getRequest(app.getBaseUrl() + urlPath, headers);
    return customHttpResponse.getResponseBody();
}
```

### 5. Benevity Onboarding Flow Integration with Athena API

The Benevity onboarding flow integrates with the Athena API at several key points to create and manage organizations, campaigns, and user accounts.

#### Service Locations
- `src/main/java/com/tiptappay/store/app/controller/benevity/store/BenevityStoreController.java`
- `src/main/java/com/tiptappay/store/app/controller/benevity/store/BenevityStoreMyAccountController.java`
- `src/main/java/com/tiptappay/store/app/service/organization/OrganizationService.java`
- `src/main/java/com/tiptappay/store/app/service/membership/MembershipService.java`

#### Integration Flow

1. **Payment Completion**
   - After a successful payment, the application creates a company/organization in Athena
   - The organization is linked to the selected Benevity cause
   - A campaign is created and associated with the organization

   ```java
   // From BenevityStoreController.java
   if (benevityStoreService.isValidPayment(responsePayment)) {
       if (benevityCause != null) {
           // Prepare Request for Organization Create
           OrganizationRequest organizationRequest =
                   organizationService.generateNewOrganizationRequest(benevityCause, responsePayment);
           // POST organization
           OrganizationResponse organizationResponse = organizationService.postOrganization(organizationRequest);

           // Store organization ID in token for later use
           if (organizationResponse != null) {
               myAccountToken.setOrganizationId(organizationResponse.getId());
               // Additional processing...
           }
       }
   }
   ```

2. **Start Collecting Option**
   - After payment completion, the user is presented with a "Start Collecting" button
   - This button appears on the order completion page (`benevity_store_complete_start_collecting.html`)
   - When clicked, it redirects the user to the account setup page

   ```html
   <!-- From benevity_store_complete_start_collecting.html -->
   <form th:action="${salesRepId != null ? '/' + salesRepId + '/benevity/start-collecting' : '/benevity/start-collecting'}" method="post">
       <button type="submit" class="btn ttp-btn-primary p-2 mb-2" style="border-radius: 10px; width: 218px">
           Start Collecting
       </button>
   </form>
   ```

   ```java
   // From BenevityStoreMyAccountController.java
   @PostMapping("/start-collecting")
   public String startCollecting() {
       return REDIRECT_BENEVITY_MY_ACCOUNT_SETUP;
   }
   ```

3. **Account Creation**
   - On the account setup page, the user enters their email and password
   - The application creates a membership in Athena API, associating the user with the organization
   - The membership includes user details and authentication information

   ```java
   // From BenevityStoreMyAccountController.java
   MembershipRequest membershipRequest = membershipService
           .generateNewMembershipRequest(myAccountToken, myAccountDTO.getPassword());

   CustomHttpResponse customHttpResponse = membershipService
           .postMembership(myAccountToken.getOrganizationId(), membershipRequest);

   if (customHttpResponse.getResponseCode() == HTTP_RESPONSE_CREATED) {
       MembershipResponse createdMembership =
               DataUtils.convertToObject(customHttpResponse.getResponseBody(), MembershipResponse.class);

       if (createdMembership != null) {
           myAccountToken.setLoginURL(createdMembership.getLoginUrl());
           // Additional processing...
       }
   }
   ```

#### API Endpoints Used

| Integration Point | Endpoint | Method | Description |
|-------------------|----------|--------|-------------|
| Organization Creation | `/api/athena/v1/companies` | POST | Creates a new organization after payment |
| Campaign Creation | `/api/athena/v1/campaigns` | POST | Creates a campaign associated with the organization |
| Membership Creation | `/api/athena/v1/companies/{organizationId}/memberships` | POST | Creates a user account associated with the organization |

#### Data Flow

1. **Payment Processing**
   - User completes payment in the Benevity store
   - Payment is processed through NetSuite
   - On successful payment, the application receives a `ResponsePayment` object

2. **Organization Creation**
   - The application creates an `OrganizationRequest` object with details from:
     - The selected Benevity cause
     - The payment response
     - User-provided information
   - The request is sent to the Athena API
   - The API returns an `OrganizationResponse` with the new organization's ID

3. **User Account Setup**
   - User clicks "Start Collecting" and is redirected to account setup
   - User provides email and password
   - The application creates a `MembershipRequest` with user details
   - The request is sent to the Athena API
   - The API returns a `MembershipResponse` with login information

4. **Login URL Generation**
   - The Athena API generates a magic link for the user
   - The link is stored in the `MyAccountToken` object
   - The user can use this link to access their account

## HTTP Service Implementation

The application uses a custom HTTP service to make requests to the Athena API:

### Service Location
`src/main/java/com/tiptappay/store/app/service/CustomHttpService.java`

This service provides methods for making GET and POST requests with appropriate headers and handling responses.

## Bundles Store Overview

The Bundles Store is a specialized store experience focused on product bundles with different payment options. It has the following key components:

### Controllers
- `BundlesStoreController.java` - Main controller for the bundles store
- `BundlesStoreWIthSalesRepController.java` - Specialized controller for sales rep interactions
- `BundlesApiController.java` - API endpoints for bundle-specific operations

### Services
- `BundlesStoreService.java` - Core service for bundles store functionality
- `BundleProductService.java` - Service for bundle product management and pricing

### Data Models
- `CartData.java` - Represents bundle items in the shopping cart
- `CheckoutData.java` - Contains checkout information including organization and shipping details
- `PaymentData.java` - Contains payment information

### Workflow
1. Product selection (`/order`)
2. Customization (`/order/customize`)
3. Cart review (`/order/cart`)
4. Shipping information (`/order/checkout/shipping`)
5. Payment processing (`/order/checkout/payment`)
6. Order completion (`/order/complete`)

## Current Athena Integration Status

Currently, the Bundles Store has limited integration with Athena. While other store types (like Benevity) create organizations and users in Athena, the Bundles Store primarily interacts with NetSuite for order processing without fully utilizing Athena's capabilities.

The `BundlesStoreService.preparePayload()` method creates a payload for NetSuite but does not currently create corresponding entities in Athena.

## Integration Requirements for Bundles Store

For the integration of the bundles store with Athena, the following endpoints will be most relevant:

1. **Company/Organization Management**:
   - Create organizations in Athena when new customers place orders
   - Retrieve organization details for returning customers
   - Update organization information when needed

2. **User Management**:
   - Create user memberships for organization administrators
   - Associate users with their respective organizations
   - Implement magic link authentication for returning users

3. **Campaign Management**:
   - Create campaigns in Athena when orders are completed
   - Associate campaigns with the appropriate organizations
   - Track campaign status and performance

4. **Sales Rep Integration**:
   - Associate sales reps with organizations in Athena
   - Track sales rep performance through Athena

## Data Flow Requirements

### Order Completion Flow
1. Customer completes order in Bundles Store
2. Order is processed in NetSuite
3. Organization is created/updated in Athena
4. User membership is created in Athena
5. Campaign is created in Athena
6. Confirmation is sent to customer

### User Authentication Flow
1. User receives magic link
2. User clicks magic link and is authenticated
3. User is associated with their organization
4. User can manage their account and view orders

## Next Steps for Bundles Store Integration

1. **Organization Creation**:
   - Implement `createOrganization()` method in `BundlesStoreService`
   - Map `CheckoutData` fields to `OrganizationRequest`
   - Call Athena API to create the organization

2. **User Management**:
   - Implement `createMembership()` method in `BundlesStoreService`
   - Create user accounts based on checkout information
   - Associate users with their organizations

3. **Campaign Creation**:
   - Enhance `createAthenaCampaign()` method for bundle-specific campaigns
   - Associate campaigns with organizations and orders

4. **Authentication**:
   - Implement magic link authentication for bundle store customers
   - Create account management screens for bundle store

5. **Implementation Plan**:
   - Add necessary service methods
   - Update controllers to use new service methods
   - Add appropriate error handling and logging
   - Create unit and integration tests for the new functionality

## Implementation Details

### 1. Organization Creation

The following code example shows how to implement organization creation in the `BundlesStoreService`:

```java
public OrganizationResponse createOrganization(CheckoutData checkoutData) {
    logger.logInfo("Inside BundlesStoreService.createOrganization()");

    // Create organization request
    OrganizationRequest organizationRequest = new OrganizationRequest();
    organizationRequest.setName(checkoutData.getOrganizationName());
    organizationRequest.setEmail(checkoutData.getEmail());
    organizationRequest.setPhone(checkoutData.getPhone());
    organizationRequest.setAddress(checkoutData.getShippingAddressLine1());
    organizationRequest.setCity(checkoutData.getShippingCity());
    organizationRequest.setState(checkoutData.getShippingState());
    organizationRequest.setZip(checkoutData.getShippingZipCode());
    organizationRequest.setCountry(checkoutData.getShippingCountry());
    organizationRequest.setStatus("active");
    organizationRequest.setType("bundle_customer");

    // Call organization service to create the organization
    return organizationService.postOrganization(organizationRequest);
}
```

### 2. User Membership Creation

The following code example shows how to implement user membership creation:

```java
public MembershipsResponseResult createMembership(int organizationId, CheckoutData checkoutData) {
    logger.logInfo("Inside BundlesStoreService.createMembership()");

    // Create membership request
    MembershipRequest membershipRequest = new MembershipRequest();
    membershipRequest.setEmail(checkoutData.getEmail());
    membershipRequest.setFirstName(extractFirstName(checkoutData.getShippingFullName()));
    membershipRequest.setLastName(extractLastName(checkoutData.getShippingFullName()));
    membershipRequest.setRole(AppConstants.MembershipRoles.MEMBERSHIP_ROLE_ADMIN);
    membershipRequest.setStatus(AppConstants.MembershipStatus.MEMBERSHIP_STATUS_ACTIVE);

    // Call membership service to create the membership
    CustomHttpResponse response = membershipService.postMembership(organizationId, membershipRequest);

    // Create token for magic link
    MyAccountToken myAccountToken = new MyAccountToken();
    myAccountToken.setEmail(checkoutData.getEmail());
    myAccountToken.setOrganizationId(organizationId);
    myAccountToken.setExpiry(AppUtil.getCurrentTimeMillis() + (60 * 60 * 1000)); // 1 hour

    // Process response
    return membershipService.handleMembershipResponse(response, myAccountToken);
}
```

### 3. Campaign Creation

The following code example shows how to enhance the campaign creation for bundles:

```java
public CustomHttpResponse createAthenaCampaign(ResponsePayment responsePayment, CheckoutData checkoutData, int organizationId) {
    String url = appService.getBaseUrl() + "/" + appService.getAthenaApiPrefix() + "/campaigns";

    Map<String, String> headers = Map.of(
            HTTP_HEADER_NAME_CONTENT_TYPE, HTTP_HEADER_VALUE_CONTENT_TYPE_JSON,
            HTTP_HEADER_NAME_AUTHORIZATION, "Api-Key " + appService.getAthenaApiKey()
    );

    Map<String, Object> campaignPayload = new HashMap<>();
    campaignPayload.put("campaignName", responsePayment.getSoNumber());
    campaignPayload.put("customerId", organizationId);
    campaignPayload.put("orderId", responsePayment.getId());
    campaignPayload.put("description", "Bundle order " + responsePayment.getSoNumber() + " for " + checkoutData.getOrganizationName());
    campaignPayload.put("source", "bundles-store");
    campaignPayload.put("status", "active");

    // Add bundle-specific information
    campaignPayload.put("startDate", checkoutData.getCampaignStartDate());

    try {
        String jsonBody = DataUtils.convertToJsonString(campaignPayload);
        return customHttpService.postRequest(url, headers, jsonBody);
    } catch (Exception e) {
        logger.logError("BundlesStoreService.createAthenaCampaign > Failed to create campaign: {}", e.getMessage());
        return null;
    }
}
```

### 4. Integration in Order Completion Flow

The following code example shows how to integrate these methods in the order completion flow:

```java
@PostMapping("/order/checkout/payment")
public String showBundlesStoreScreen5Post(@RequestParam("cart") String cartJson,
                                         @RequestParam("checkout") String checkoutJson,
                                         @RequestParam("payment") String paymentJson,
                                         HttpServletResponse response) {
    List<CartData> cartData = DataUtils.convertToObjectList(cartJson, CartData.class);
    CheckoutData checkoutData = DataUtils.convertToObject(checkoutJson, CheckoutData.class);
    PaymentData paymentData = DataUtils.convertToObject(paymentJson, PaymentData.class);

    // Process payment with NetSuite
    PaymentReqRes paymentReqRes = new PaymentReqRes();
    Payload payload = bundlesStoreService.preparePayload(null, checkoutData, cartData, paymentData, "completeOrderWithPayment");
    paymentReqRes.setRequest(payload);

    // Process the payment
    CustomHttpResponse customHttpResponse = oauthService.runApiRequest(DataUtils.convertToJsonString(payload));
    ResponsePayment responsePayment = dataProcessorService.handleResponse(customHttpResponse.getResponseBody());
    paymentReqRes.setResponse(responsePayment);

    // If payment successful, create organization and campaign in Athena
    if (responsePayment != null && responsePayment.isSuccess() && responsePayment.getSoNumber() != null) {
        // Create organization
        OrganizationResponse organizationResponse = bundlesStoreService.createOrganization(checkoutData);

        if (organizationResponse != null && organizationResponse.getId() > 0) {
            int organizationId = organizationResponse.getId();

            // Create membership
            bundlesStoreService.createMembership(organizationId, checkoutData);

            // Create campaign
            bundlesStoreService.createAthenaCampaign(responsePayment, checkoutData, organizationId);
        }
    }

    // Store response and redirect
    cookieService.createCookie(response, "responsePaymentBody", customHttpResponse.getResponseBody());
    return REDIRECT_TTP_BUNDLES_ORDER_COMPLETE;
}
```

## Conclusion

Integrating the Bundles Store with Athena will provide several benefits:

1. **Unified Customer Management**: All customer data will be stored in a central system
2. **Enhanced User Experience**: Customers will have access to account management features
3. **Improved Campaign Tracking**: Campaigns will be properly tracked and managed
4. **Better Sales Rep Integration**: Sales reps can be associated with organizations and campaigns

The implementation will require adding new service methods to the `BundlesStoreService` and updating the controllers to use these methods. The existing Athena API integration code can be leveraged to minimize development effort.
