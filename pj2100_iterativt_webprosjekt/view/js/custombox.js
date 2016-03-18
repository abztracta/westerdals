(function($)
{
	$('.btn-custombox').on('click', function(e)
	{
		$.fn.custombox(this,
		{
			effect: 'fadein'
		});

		e.preventDefault();
	});
})(jQuery);
