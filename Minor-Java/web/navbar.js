//// Mobile menu functionality
//var mobileMenuBtn = document.querySelector('.mobile-menu-btn');
//var navLinks = document.querySelector('.nav-links');
//
//mobileMenuBtn.addEventListener('click', function () {
//    navLinks.classList.toggle('active');
//    
//    // Animate hamburger menu
//    var spans = mobileMenuBtn.querySelectorAll('span');
//    for (var i = 0; i < spans.length; i++) {
//        spans[i].classList.toggle('active');
//    }
//});
////
////// Change navbar background on scroll
//window.addEventListener('scroll', function () {
//    var navbar = document.querySelector('.navbar');
//    if (window.scrollY > 50) {
//        navbar.style.backgroundColor = '#ffffff';
//        navbar.style.boxShadow = '0 2px 10px rgba(0, 0, 0, 0.1)';
//    } else {
//        navbar.style.backgroundColor = 'transparent';
//        navbar.style.boxShadow = 'none';
//    }
//});
//
//// Update active nav link on scroll
//window.addEventListener('scroll', function () {
//    var sections = document.querySelectorAll('section');
//    var navLinks = document.querySelectorAll('.nav-links a');
//    
//    for (var i = 0; i < sections.length; i++) {
//        var section = sections[i];
//        var sectionTop = section.offsetTop - 100;
//        var sectionHeight = section.clientHeight;
//        var scrollPosition = window.scrollY;
//        
//        if (scrollPosition >= sectionTop && scrollPosition < sectionTop + sectionHeight) {
//            for (var j = 0; j < navLinks.length; j++) {
//                var link = navLinks[j];
//                link.classList.remove('active');
//                if (link.getAttribute('href') === '#' + section.getAttribute('id')) {
//                    link.classList.add('active');
//                }
//            }
//        }
//    }
//});
