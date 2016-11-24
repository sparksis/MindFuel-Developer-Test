/**
 * Catch-All location for code which could be called inline. This code will run
 * AFTER all other libraries are loaded but but typically before angular has
 * bootstrapped the page.
 */
$(function() {
	$('#drawing').sketch();
});