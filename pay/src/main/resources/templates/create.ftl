<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <title>支付测试页面</title>

</head>
<body>
<h1>支付测试页面</h1>
<div id="myQrcode"></div>
<p>Render in canvas</p>

<script src="https://cdn.bootcdn.net/ajax/libs/jquery/1.5.1/jquery.min.js"></script>
<script src="https://cdn.bootcdn.net/ajax/libs/qrcodejs/1.0.0/qrcode.min.js"></script>


<script>
    var qrcode = new QRCode("myQrcode");
    qrcode.makeCode("${codeUrl}");
</script>
</body>

</html>