// Smooth scrolling for navigation links
document.addEventListener('DOMContentLoaded', function () {
    var anchors = document.querySelectorAll('a[href^="#"]');
    for (var i = 0; i < anchors.length; i++) {
        var anchor = anchors[i];
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            var targetId = this.getAttribute('href').substring(1);
            var targetElement = document.getElementById(targetId);
            
            if (targetElement) {
                targetElement.scrollIntoView({
                    behavior: 'smooth'
                });
            }
        });
    }
});

// Animate elements on scroll
function animateOnScroll() {
    var elements = document.querySelectorAll('.about-card, .get-started-section');
    for (var i = 0; i < elements.length; i++) {
        var element = elements[i];
        var elementTop = element.getBoundingClientRect().top;
        var elementVisible = 150;
        
        if (elementTop < window.innerHeight - elementVisible) {
            element.classList.add('animate');
        }
    }
}

// Handle navigation button clicks for 'Sign In'
var signInButtons = document.querySelectorAll('.sign-in-btn');
for (var i = 0; i < signInButtons.length; i++) {
    var button = signInButtons[i];
    button.addEventListener('click', function () {
        window.location.href = './sign-in.html';
    });
}

// Handle clicks for 'Primary Buttons' like "Get Started"
var primaryButtons = document.querySelectorAll('.primary-btn');
for (var i = 0; i < primaryButtons.length; i++) {
    var button = primaryButtons[i];
    button.addEventListener('click', function (e) {
        var action = e.target.textContent.toLowerCase();
        if (action.includes('get started') || action.includes('start free trial')) {
            window.location.href = './sign-up.html';
        }
    });
}

// Handle clicks for 'Secondary Buttons' (e.g., "Learn More")
var secondaryButtons = document.querySelectorAll('.secondary-btn');
for (var i = 0; i < secondaryButtons.length; i++) {
    var button = secondaryButtons[i];
    button.addEventListener('click', function () {
        var aboutSection = document.getElementById('about');
        if (aboutSection) {
            aboutSection.scrollIntoView({ behavior: 'smooth' });
        }
    });
}


// Listen to scroll events
window.addEventListener('scroll', animateOnScroll);
window.addEventListener('load', animateOnScroll);
