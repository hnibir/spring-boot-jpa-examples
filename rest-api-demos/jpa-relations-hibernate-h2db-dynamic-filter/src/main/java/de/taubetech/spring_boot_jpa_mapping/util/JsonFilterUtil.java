package de.taubetech.spring_boot_jpa_mapping.util;

import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JsonFilterUtil {

    public static class FilterPerson {
        public static SimpleFilterProvider getMiniPersonFilter() {
            // Dynamic filter for Person model
            SimpleBeanPropertyFilter filterPerson = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "name", "age", "personProfile");

            // Dynamic filter for PersonProfile model
            SimpleBeanPropertyFilter filterProfile = SimpleBeanPropertyFilter.filterOutAllExcept("id", "city", "country");

            // Dynamic filter for Party model
            SimpleBeanPropertyFilter filterParty = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "location", "date"); // "id", "location", "date", "persons

            // Instantiate FilterProvider and add defined filters to it
            SimpleFilterProvider filters = new SimpleFilterProvider();
            filters.addFilter("personFilter", filterPerson);
            filters.addFilter("personProfileFilter", filterProfile);
            filters.addFilter("partyFilter", filterParty);

            return filters;
        }

        public static SimpleFilterProvider getDetailPersonFilter() {
            // Dynamic filter for Person model
            SimpleBeanPropertyFilter filterPerson = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "name", "age", "personProfile", "skills", "parties");

            // Dynamic filter for Skill model
            SimpleBeanPropertyFilter filterSkill = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "name", "level");


            // Dynamic filter for PersonProfile model
            SimpleBeanPropertyFilter filterProfile = SimpleBeanPropertyFilter.filterOutAllExcept("id", "city", "country");

            // Dynamic filter for Party model
            SimpleBeanPropertyFilter filterParty = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "location", "date"); // "id", "location", "date", "persons

            // Instantiate FilterProvider and add defined filters to it
            SimpleFilterProvider filters = new SimpleFilterProvider();
            filters.addFilter("personFilter", filterPerson);
            filters.addFilter("personProfileFilter", filterProfile);
            filters.addFilter("partyFilter", filterParty);
            filters.addFilter("skillFilter", filterSkill);

            return filters;
        }
    }

    public static class FilterParty {
        public static SimpleFilterProvider getMiniPartyFilter() {
            // Dynamic filter for Person model
            SimpleBeanPropertyFilter filterPerson = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "name", "age", "personProfile", "skills"
            );

            // Dynamic filter for PersonProfile model
            SimpleBeanPropertyFilter filterProfile = SimpleBeanPropertyFilter.filterOutAllExcept("id", "city", "country");

            // Dynamic filter for Party model
            SimpleBeanPropertyFilter filterParty = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "location", "date"); // "id", "location", "date", "persons"

            // Instantiate FilterProvider and add defined filters to it
            SimpleFilterProvider filters = new SimpleFilterProvider();
            filters.addFilter("personFilter", filterPerson);
            filters.addFilter("personProfileFilter", filterProfile);
            filters.addFilter("partyFilter", filterParty);

            return filters;
        }

        public static SimpleFilterProvider getDetailPartyFilter() {
            // Dynamic filter for Person model
            SimpleBeanPropertyFilter filterPerson = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "name", "age", "personProfile", "skills"
            );

            // Dynamic filter for PersonProfile model
            SimpleBeanPropertyFilter filterProfile = SimpleBeanPropertyFilter.filterOutAllExcept("id", "city", "country");

            // Dynamic filter for Party model
            SimpleBeanPropertyFilter filterParty = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "location", "date", "persons"); // "id", "location", "date", "persons"

            // Instantiate FilterProvider and add defined filters to it
            SimpleFilterProvider filters = new SimpleFilterProvider();
            filters.addFilter("personFilter", filterPerson);
            filters.addFilter("personProfileFilter", filterProfile);
            filters.addFilter("partyFilter", filterParty);

            return filters;
        }
    }

    public static class FilterPersonProfile {
        public static SimpleFilterProvider getMiniProfileFilter() {
            // Dynamic filter for Person model
            SimpleBeanPropertyFilter filterPerson = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "age");

            // Dynamic filter for PersonProfile model
            SimpleBeanPropertyFilter filterProfile = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "city", "country", "person");

            // Dynamic filter for Party model
            SimpleBeanPropertyFilter filterParty = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "location", "date"); // "id", "location", "date", "persons"

            // Instantiate FilterProvider and add defined filters to it
            SimpleFilterProvider filters = new SimpleFilterProvider();
            filters.addFilter("personFilter", filterPerson);
            filters.addFilter("personProfileFilter", filterProfile);
            filters.addFilter("partyFilter", filterParty);

            return filters;
        }

        public static SimpleFilterProvider getDetailProfileFilter() {
            // Dynamic filter for Person model
            SimpleBeanPropertyFilter filterPerson = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "name", "age", "skills", "parties"
            );

            // Dynamic filter for PersonProfile model
            SimpleBeanPropertyFilter filterProfile = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "city", "country", "person");

            // Dynamic filter for Party model
            SimpleBeanPropertyFilter filterParty = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "location", "date"); // "id", "location", "date", "persons"

            // Dynamic filter for Skill model
            SimpleBeanPropertyFilter filterSkill = SimpleBeanPropertyFilter.filterOutAllExcept(
                    "id", "name", "level");


            // Instantiate FilterProvider and add defined filters to it
            SimpleFilterProvider filters = new SimpleFilterProvider();
            filters.addFilter("personFilter", filterPerson);
            filters.addFilter("personProfileFilter", filterProfile);
            filters.addFilter("partyFilter", filterParty);
            filters.addFilter("skillFilter", filterSkill);

            return filters;
        }

    }

}
