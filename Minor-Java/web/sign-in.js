// Captcha generation
function generateCaptcha() {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
    var captcha = '';
    for (var i = 0; i < 6; i++) {
        captcha += chars.charAt(Math.floor(Math.random() * chars.length));
    }
    document.getElementById('captcha').textContent = captcha;
    return captcha;
}

// Timer functionality
function startTimer(duration, display) {
    var timer = duration;
    var interval = setInterval(function () {
        var minutes = parseInt(timer / 60, 10);
        var seconds = parseInt(timer % 60, 10);

        display.textContent = minutes.toString().padStart(2, '0') + ':' + seconds.toString().padStart(2, '0');

        if (--timer < 0) {
            clearInterval(interval);
            display.textContent = 'OTP Expired';
        }
    }, 1000);
    return interval;
}

// Form handling
var form = document.getElementById('signInForm');
var otpSection = document.getElementById('otpSection');
var currentCaptcha = generateCaptcha();
var timerInterval;

form.addEventListener('submit', function (e) {
    e.preventDefault();
    var phone = document.getElementById('phone').value;
    var captchaInput = document.getElementById('captchaInput').value;

    if (captchaInput !== currentCaptcha) {
        alert('Invalid Captcha');
        currentCaptcha = generateCaptcha();
        return;
    }

    if (!otpSection.classList.contains('hidden')) {
        // Handle OTP verification
        var otp = document.getElementById('otp').value;
        // Add OTP verification logic here
        console.log('Verifying OTP:', otp);
    } else {
        // Show OTP section and start timer
        otpSection.classList.remove('hidden');
        var timerDisplay = document.getElementById('timer');
        timerInterval = startTimer(120, timerDisplay);
        form.querySelector('button[type="submit"]').textContent = 'Verify OTP';
    }
});

document.getElementById('resendOtp').addEventListener('click', function () {
    clearInterval(timerInterval);
    var timerDisplay = document.getElementById('timer');
    timerInterval = startTimer(120, timerDisplay);
    // Add OTP resend logic here
});
