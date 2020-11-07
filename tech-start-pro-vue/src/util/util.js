export default {
    numberFormat: function (number, locale, options) {
        if (number != null && typeof number === 'number') {
            locale = locale || 'pt';
            options = options || {
                minimumFractionDigits: 2,
                maximumFractionDigits: 2,
            };
            return number.toLocaleString(locale, options);
        }
        return '';
    },
};
