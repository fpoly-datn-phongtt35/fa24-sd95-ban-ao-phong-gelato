<!doctype html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
</head>
<body class="container">

<form action="/sinh-vien/add" method="post">
    <div class="mb-3">
        <label class="form-label">ID</label>
        <input type="text" class="form-control" name="id">
    </div>
    <div class="mb-3">
        <label class="form-label">Ho ten</label>
        <input type="text" class="form-control" name="hoTen">
    </div>
    <div class="mb-3">
        <label class="form-label">Dia chi</label>
        <input type="text" class="form-control" name="diaChi">
    </div>
    <div class="mb-3">
        <label class="form-label">Gioi tinh</label>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Nam" name="gioiTinh">
            <label class="form-check-label">
                Nam
            </label>
        </div>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Nu" name="gioiTinh">
            <label class="form-check-label">
                Nu
            </label>
        </div>
    </div>
    <div class="mb-3">
        <label class="form-label">Trang thai</label>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Hoat dong" name="trangThai">
            <label class="form-check-label">
                Hoat dong
            </label>
        </div>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Ngung hoat dong" name="trangThai">
            <label class="form-check-label">
                Ngung hoat dong
            </label>
        </div>

    </div>

    <button type="submit" class="btn btn-primary">Submit</button>
</form>
<table class="table">
    <thead>
    <tr>
        <td>Stt</td>
        <td>Id</td>
        <td>Ho ten</td>
        <td>Dia chi</td>
        <td>Gioi Tinh</td>
        <td>Trang Thai</td>
        <td>Action</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${listSinhVien}" var="sinhVien" varStatus="i">
        <tr>
            <td>${i.index}</td>
            <td>${sinhVien.id}</td>
            <td>${sinhVien.hoTen}</td>
            <td>${sinhVien.diaChi}</td>
            <td>${sinhVien.gioiTinh}</td>
            <td>${sinhVien.trangThai}</td>
            <td>
                <a class="btn btn-dark" href="/sinh-vien/delete?id=${sinhVien.id}">Xoa</a>
                <a class="btn btn-dark" href="/sinh-vien/edit?id=${sinhVien.id}">Edit</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>
</body>
</html>