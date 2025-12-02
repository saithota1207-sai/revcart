@echo off
echo Testing Email Service...

set /p email="Enter your email address: "

echo.
echo Sending test email to: %email%
curl -X POST "http://localhost:8081/api/test/send-email?email=%email%"

echo.
echo.
echo Check your email inbox and spam folder for the test OTP email.
pause