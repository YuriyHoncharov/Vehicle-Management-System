package ua.com.foxminded.yuriy.carrestservice.properties;

public class SwaggerDescription {
  
  public static final String MODEL_GET_ALL_DESCRIPTION = """
          
      Get entire list of Models.\

       Parameters (OPTIONAL) :
       -\s\
      **page** : Page of pagination, **default value = 0**, example = 10.
       -\s\
      **size** : Size of the page for pagination, **default value = 20**, example = 10
       -\s\
      **sort** : Sorting criteria in the format : property **{id // name // brand}** , asc | desc.\s\
      **Default is UNSORTED**. Multiple sort criteria are supported,\s\
      **example = name,asc.**""";

  public static final String BRAND_GET_ALL_DESCRIPTION = """
          
      Get entire list of Models.

       Parameters (OPTIONAL) :
       \
      - **page** : Page of pagination, **default value = 0**, example = 10.
       - **size** : Size of the page for pagination, **default value = 20**, example = 10
       \
      - **sort** : Sorting criteria in the format : property **{id // name // models}** , asc|desc.\s\
      **Default is UNSORTED**. Multiple sort criteria are supported, **example = name,asc.**""";

  public static final String CATEGORY_GET_ALL_DESCRIPTION = """
          
      Get entire list of Models.

       Parameters (OPTIONAL) :
       \
      - **page** : Page of pagination, **default value = 0**, example = 10.
       - **size** : Size of the page for pagination, **default value = 20**, example = 10
       \
      - **sort** : Sorting criteria in the format : property **{id // name}** , asc|desc.\s\
      **Default is UNSORTED**. Multiple sort criteria are supported, **example = name,asc.**""";

  public static final String CAR_GET_ALL_DESCRIPTION = """
          
      Retrieve a list of models with various filter and sort options.

       Parameters (OPTIONAL):
       \
      - **page** : Page number for pagination (default: 0), **example: 5.**
       - **size** : Page size for pagination (default: 10), **example: 10**
       \
      - **sortOrder** : Sorting order, 'asc' or 'desc' (default: asc), **example: asc**.
       \
      - **sortBy** : Property to sort by **[CASE SENSITIVE : id // objectId // brand // model // productionYear]** (default : model), **example: objectId**.
       \
      - **brand** : Filter by brand name, **example: Audi. Or more than one, example : Audi,BMW**
       \
      - **category** : Filter by category, **example: SUV. Or more than one, example : Sedan,SUV**
       \
      - **model** : Filter by model name, **example: Q3. Or more than one, example : Q3,X3**
       \
      - **year** : Filter by production year. Options :
        1)\s\
      **Between** two years **[y0,y1]** (e.g., 2000, 2020)
        2)\s\
      **After** a specific year **[y0]** (e.g., 2015)
        3)\s\
      **Before** a specific year **[0, y1]** (e.g., 0, 2015), example: 0,2020.""";
}
