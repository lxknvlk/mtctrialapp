package com.example.mtctrial.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.content.Context;

public class FlagName {

    private static FlagName sFlagName = null;
    private static final Map<String, String> sNationalitiesMap;
    private static final Map<String, String> sCountriesMap;
    static
    {
        Map<String, String> nationalitiesMap = new HashMap<String, String>() {

            private static final long serialVersionUID = 1L;

            {
                put("Afghan", "afghanistan");
                put("Albanian", "albania");
                put("Algerian", "algeria");
                put("American", "united_states");
                put("American Samoan", "american_Samoa");
                put("Andorran", "andorra");
                put("Angolan", "angola");
                put("Anguillan", "anguilla");
                put("Antiguan", "antigua_and_barbuda");
                put("Argentine", "argentina");
                put("Armenian", "armenia");
                put("Aruban", "aruba");
                put("Australian", "australia");
                put("Austrian", "austria");
                put("Azerbaijani", "azerbaijan");
                put("Bahamian", "bahamas");
                put("Bahraini", "bahrain");
                put("Bangladeshi", "bangladesh");
                put("Barbadian", "barbados");
                put("Basotho", "lesotho");
                put("Belarusian", "belarus");
                put("Belgian", "belgium");
                put("Belizean", "belize");
                put("Beninese", "benin");
                put("Bermudian", "bermuda");
                put("Bhutanese", "bhutan");
                put("Bolivian", "bolivia");
                put("Bosnian", "bosnia_and_herzegovina");
                put("Botswanan", "botswana");
                put("Brazilian", "brazil");
                put("British Virgin Islander", "british_virgin_islands");
                put("Bruneian", "brunei");
                put("Bulgarian", "bulgaria");
                put("Burkinabe", "burkina_Faso");
                put("Burmese", "myanmar");
                put("Burundian", "burundi");
                put("Cambodian", "cambodia");
                put("Cameroonian", "cameroon");
                put("Canadian", "canada");
                put("Cape Verdean", "cape_verde");
                put("Caymanian", "cayman_islands");
                put("Central African", "central_african_republic");
                put("Chadian", "chad");
                put("Chilean", "chile");
                put("Chinese", "china");
                put("Colombian", "colombia");
                put("Comoran", "comoros");
                put("Congolese", "republic_of_the_congo");
                put("Congolese (DRC)", "democratic_republic_of_the_congo");
                put("Cook Islander", "cook_islands");
                put("Costa Rican", "costa_rica");
                put("Croatian", "croatia");
                put("Cuban", "cuba");
                put("Cura??aoan", "curacao");
                put("Cypriot", "cyprus");
                put("Czech", "czech_republic");
                put("Danish", "denmark");
                put("Djiboutian", "djibouti");
                put("Dominican", "dominica");
                put("Dominican Republic", "dominican_republic");
                put("Dutch", "netherlands");
                put("Ecuadorian", "ecuador");
                put("Egyptian", "egypt");
                put("Emirati", "united_arab_emirates");
                put("English", "england");
                put("Equatoguinean", "equatorial_guinea");
                put("Eritrean", "eritrea");
                put("Estonian", "estonia");
                put("Ethiopian", "ethiopia");
                put("Faroese", "faroes");
                put("Fijian", "fiji");
                put("Finnish", "finland");
                put("French", "france");
                put("Gabonese", "gabon");
                put("Gambian", "gambia");
                put("Georgian", "georgia");
                put("German", "germany");
                put("Ghanaian", "ghana");
                put("Gibraltarian", "gibraltar");
                put("Greek", "greece");
                put("Grenadian", "grenada");
                put("Guamanian", "guam");
                put("Guatemalan", "guatemala");
                put("Guinean", "guinea");
                put("Guinean (Bissau)", "guinea_bissau");
                put("Guyanese", "guyana");
                put("Haitian", "haiti");
                put("Honduran", "honduras");
                put("Hong Kong", "hong_kong");
                put("Hungarian", "hungary");
                put("Icelandic", "iceland");
                put("Indian", "india");
                put("Indonesian", "indonesia");
                put("Iranian", "iran");
                put("Iraqi", "iraq");
                put("Irish", "ireland");
                put("Israeli", "israel");
                put("Italian", "italy");
                put("Ivorian", "cote_divoire");
                put("Jamaican", "jamaica");
                put("Japanese", "japan");
                put("Jordanian", "jordan");
                put("Kazakhstani", "kazakhstan");
                put("Kenyan", "kenya");
                //put("kiribatien" :
                put("Kittitian", "saint_kitts_and_nevis");
                put("Kosovar", "kosovo");
                put("Kuwaiti", "kuwait");
                put("Kyrgyzstani", "kyrgyzstan");
                put("Laotian", "laos");
                put("Latvian", "latvia");
                put("Lebanese", "lebanon");
                put("Liberian", "liberia");
                put("Libyan", "libya");
                put("Liechtenstein", "liechtenstein");
                put("Lithuanian", "lithuania");
                put("Macanese", "macau");
                put("Macedonian", "macedonia");
                put("Mahoran", "mayotte");
                put("Malagasy", "madagascar");
                put("Malawian", "malawi");
                put("Malaysian", "malaysia");
                put("Maldivian", "maldives");
                put("Malian", "mali");
                put("Maltese", "malta");
                put("Martiniquais", "martinique");
                put("Mauritanian", "mauritania");
                put("Mauritian", "mauritius");
                put("Mexican", "mexico");
                put("Moldovan", "moldova");
                put("Mongolian", "mongolia");
                put("Montenegrin", "montenegro");
                put("Montserratian", "montserrat");
                put("Moroccan", "morocco");
                put("Mozambican", "mozambique");
                put("Namibian", "namibia");
                put("Nepali", "nepal");
                put("New Caledonian", "new_caledonia");
                put("New Zealand", "new_zealand");
                put("Ni_Vanuatu", "vanuatu");
                put("Nicaraguan", "nicaragua");
                put("Nigerian", "nigeria");
                put("Nigerien", "niger");
                put("North Korean", "north_korea");
                put("Northern Irish", "northern_ireland");
                put("Norwegian", "norway");
                put("Omani", "oman");
                put("Pakistani", "pakistan");
                put("Palestinian", "palestine");
                put("Panamanian", "panama");
                put("Papua New Guinean", "papua_new_guinea");
                put("Paraguayan", "paraguay");
                put("Peruvian", "peru");
                put("Philippine", "philippines");
                put("Polish", "poland");
                put("Portuguese", "portugal");
                put("Puerto Rican", "puerto_rico");
                put("Qatari", "qatar");
                put("R??unionese", "france");
                put("Romanian", "romania");
                put("Rwandan", "rwanda");
                put("Russian", "russia");
                put("Saint Lucian", "saint_lucia");
                put("Salvadoran", "el_salvador");
                put("Sammarinese", "san_marino");
                put("S??o Tom??an", "sao_tome_and_principe");
                put("Saudi", "saudi_arabia");
                put("Samoan", "samoa");
                put("Scottish", "scotland");
                put("Senegalese", "senegal");
                put("Serbian", "serbia");
                put("Seychellois", "seychelles");
                put("Sierra Leonean", "sierra_leone");
                put("Singaporean", "singapore");
                put("Slovak", "slovakia");
                put("Slovenian", "slovenia");
                put("Solomon Island", "solomon_islands");
                put("Somali", "Somalia");
                put("South African", "south_africa");
                put("South Korean", "south_korea");
                put("South Sudanese", "south_sudan");
                put("Spanish", "spain");
                put("Sudanese", "sudan");
                put("St. Vincentian", "saint_vincent_and_the_grenadines");
                put("Surinamese", "suriname");
                put("Swazi", "swaziland");
                put("Swedish", "sweden");
                put("Swiss", "switzerland");
                put("Syrian", "syria");
                put("Tahitian", "tahiti");
                put("Taiwanese", "taiwan");
                put("Tajikistani", "tajikistan");
                put("Tanzanian", "tanzania");
                put("Thai", "thailand");
                put("Timorese", "east_timor");
                put("Togolese", "togo");
                put("Tongan", "tonga");
                put("Trinidadian", "trinidad_and_tobago");
                put("Tunisian", "tunisia");
                put("Turkish", "turkey");
                put("Turkmen", "turkmenistan");
                put("Turks and Caicos", "turks_and_caicos_islands");
                put("Ugandan", "uganda");
                put("Ukrainian", "ukraine");
                put("Uruguayan", "uruguay");
                put("US Virgin Islander", "us_virgin_islands");
                put("Uzbekistani", "uzbekistan");
                put("Venezuelan", "venezuela");
                put("Vietnamese", "vietnam");
                put("Welsh", "wales");
                put("Yemeni", "yemen");
                put("Zambian", "zambia");
                put("Zimbabwean", "zimbabwe");
            }
        };

        sNationalitiesMap = Collections.unmodifiableMap(nationalitiesMap);

        Map<String, String> countriesMap = new HashMap<String, String>() {

            private static final long serialVersionUID = 1L;

            {
                put("American Samoa", "american_samoa");
                put("Antigua and Barbuda", "antigua_and_barbuda");
                put("Aruba", "aruba");
                put("Basque Country", "basque_country");
                put("Belgium", "belgium");
                put("Bolivia", "bolivia");
                put("Bosnia and Herzegovina", "bosnia_and_herzegovina");
                put("Burkina Faso", "burkina_faso");
                put("Canary Islands", "canary_islands");
                put("Cayman Islands", "cayman_islands");
                put("Cape Verde", "cape_verde");
                put("Central African R", "central_african_republic");
                put("Chinese Taipei", "taiwan");
                put("Congo", "democratic_republic_of_the_congo");
                put("Costa Rica", "costa_rica");
                put("Cura??ao", "curacao");
                put("Czech Republic", "czech_republic");
                put("Dominican Republic", "dominican_republic");
                put("DR Congo", "democratic_republic_of_the_congo");
                put("East Timor", "east_timor");
                put("El Salvador", "el_salvador");
                put("England", "england");
                put("Equatorial Guinea", "equatorial_guinea");
                put("Faroe Islands", "faroes");
                put("France", "france");
                put("French Guiana", "french_guiana");
                put("Hong Kong", "hong_kong");
                put("Ireland", "ireland");
                put("Ivory Coast", "cote_divoire");
                put("Germany", "germany");
                put("Martinica", "martinique");
                put("Mexico", "mexico");
                put("Netherlands Antilles", "netherlands_antilles");
                put("New Caledonia", "new_caledonia");
                put("New Zealand", "new_zealand");
                put("North Korea", "north_korea");
                put("Northern Cyprus", "northern_cyprus");
                put("Northern Ireland", "northern_ireland");
                put("Northern Mariana", "northern_mariana_islands");
                put("Papua New Guinea", "papua_new_guinea");
                put("Puerto Rico", "puerto_rico");
                put("Republic of Ireland", "ireland");
                put("R??union", "france");
                put("Saint Lucia", "saint_lucia");
                put("Saint Kitts and Nevis", "saint_kitts_and_nevis");
                put("Saint Vincent", "saint_vincent_and_the_grenadines");
                put("San Marino", "san_marino");
                put("S??o Tom?? and Pr??ncipe", "sao_tome_and_principe");
                put("Saudi Arabia", "saudi_arabia");
                put("Sierra Leone", "sierra_leone");
                put("Solomon Islands", "solomon_islands");
                put("South Africa", "south_africa");
                put("South Korea", "south_korea");
                //put("South Yemen", "");
                put("Sri Lanka", "sri_lanka");
                put("Tibet", "China"); // Unfortunately
                put("Trinidad and Tobago", "trinidad_and_tobago");
                put("Turks & Caicos Islands", "turks_and_caicos_islands");
                put("United Arab Emirates", "united_arab_emirates");
                put("United States", "united_states");
                put("Wallis & Futuna", "wallis_and_futuna");
                put("Zanzibar", "tanzania");
            }
        };

        sCountriesMap = Collections.unmodifiableMap(countriesMap);
    }

    // A private Constructor prevents any other class from instantiating.
    private FlagName(){ }

    // Static 'instance' method
    public static FlagName getInstance() {

        if (sFlagName == null)
        {
            sFlagName = new FlagName();
        }

        return sFlagName;
    }

    private String checkResourcesForDrawable(String filename, Context context)
    {
        if (context.getResources().getIdentifier(filename, "drawable", context.getPackageName()) == 0)
        {
            return "unknown";
        }

        return filename;
    }

    public String getFlagFilenameForNationality(String nationality, Context context)
    {
        String filename = sNationalitiesMap.get(nationality);

        if (filename == null)
        {
            // try to see if it exists anyway in the resources
            filename = this.checkResourcesForDrawable(createFilename(nationality), context);
        }

        return filename;
    }

    public String getFlagFilenameForCountry(String country, Context context)
    {
        String filename;

        if (country == "")
        {
            filename = "unknown";
        }
        else
        {
            filename = sCountriesMap.get(country);

            if (filename == null)
            {
                // try to see if it exists anyway in the resources
                filename = this.checkResourcesForDrawable(createFilename(country), context);
            }
        }

        return filename;
    }

    // convert a string to lowercase and replace spaces with "_"
    protected String createFilename(String text)
    {
        return text.toLowerCase(Locale.FRENCH).replace(' ', '_');
    }

}