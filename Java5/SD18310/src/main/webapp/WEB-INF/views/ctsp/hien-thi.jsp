<!doctype html>
<html lang="en">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-body-tertiary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/san-pham/hien-thi">Home</a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">
                        Dropdown
                    </a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="/ctsp/hien-thi">Sản phẩm chi tiết</a></li>
                        <li><a class="dropdown-item" href="/danh-muc/hien-thi">Danh mục</a></li>
                        <li><a class="dropdown-item" href="/hoa-don/hien-thi">Hóa đơn</a></li>
                        <li><a class="dropdown-item" href="/hdct/hien-thi">Hóa đơn chi tiet</a></li>
                        <li><a class="dropdown-item" href="/khach-hang/hien-thi">Khách hàng</a></li>
                        <li><a class="dropdown-item" href="/mau-sac/hien-thi">Màu sắc</a></li>
                        <li><a class="dropdown-item" href="/size/hien-thi">Size</a></li>
                    </ul>
                </li>
            </ul>
            <form class="d-flex" role="search">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>

<form action="/ctsp/add" method="post">
    <div class="mb-3">
        <label for="disabledSelect" class="form-label">Sản Phẩm</label>
        <select id="disabledSelect" class="form-select" name="sanPham">
            <c:forEach items="${lopSP}" var="sp">
                <option value="${sp.id}">${sp.ten_san_pham}</option>
            </c:forEach>
        </select>
    </div>
    <div class="mb-3">
        <label for="disabledSelect" class="form-label">Màu Sắc</label>
        <select id="disabledSelect" class="form-select" name="mau_sac">
            <c:forEach items="${lopMS}" var="ms">
                <option value="${ms.id}">${ms.ten_mau}</option>
            </c:forEach>
        </select>
    </div>
    <div class="mb-3">
        <label for="disabledSelect" class="form-label">Size</label>
        <select id="disabledSelect" class="form-select" name="size">
            <c:forEach items="${lopSize}" var="s">
                <option value="${s.id}">${s.ten_size}</option>
            </c:forEach>
        </select>
    </div>
    <div class="mb-3">
        <label class="form-label">Giá Bán</label>
        <input type="number" class="form-control" name="gia_ban">
    </div>
    <div class="mb-3">
        <label class="form-label">Số Lượng Tồn</label>
        <input type="number" class="form-control" name="so_luong_ton">
    </div>
    <div class="row">
        <p class="col-4"> Trang thai
        </p>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Active" name="trang_thai">
            <label class="form-check-label">
                Active
            </label>
        </div>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Inactive" name="trang_thai">
            <label class="form-check-label">
                Inactive
            </label>
        </div>
    </div>
    <button type="submit" class="btn btn" style="background-color: chocolate">Submit</button>
</form>

<table class="table table-striped">
    <thead>
    <tr>
        <td>id</td>
        <td>Tên Sản Phẩm</td>
        <td>Màu Sắc</td>
        <td>Size</td>
        <td>Giá Bán</td>
        <td>Số Lượng Tồn</td>
        <td>Trạng Thái</td>
        <td>Action</td>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${list}" var="ctsp">
        <tr>
            <td>${ctsp.id}</td>
            <td>${ctsp.sanPham.ten_san_pham}</td>
            <td>${ctsp.mauSac.ten_mau}</td>
            <td>${ctsp.size.ten_size}</td>
            <td>${ctsp.gia_ban}</td>
            <td>${ctsp.so_luong_ton}</td>
            <td>${ctsp.trang_thai}</td>
            <td>
                <a href="/ctsp/delete?id=${ctsp.id}" class="btn btn-danger">Delete</a>
                <a href="/ctsp/detail?id=${ctsp.id}" class="btn btn-warning">Detail</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%--<a href="/san-pham/hien-thi?page=${page.number - 1}">pre</a>--%>
<%--<a href="/san-pham/hien-thi?page=${page.number +1}">next</a>--%>
</body>
</html>