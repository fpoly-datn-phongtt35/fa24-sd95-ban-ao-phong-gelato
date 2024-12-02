<!doctype html>
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

<form action="/sinh-vien/update" method="post">
    <div class="mb-3">
        <label class="form-label">ID</label>
        <input type="text" class="form-control" name="id" value="${sinhVien.id}">
    </div>
    <div class="mb-3">
        <label class="form-label">Ho ten</label>
        <input type="text" class="form-control" name="hoTen" value="${sinhVien.hoTen}">
    </div>
    <div class="mb-3">
        <label class="form-label">Dia chi</label>
        <input type="text" class="form-control" name="diaChi" value="${sinhVien.diaChi}">
    </div>
    <div class="mb-3">
        <label class="form-label">Gioi tinh</label>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Nam" name="gioiTinh"
            ${sinhVien.gioiTinh == 'Nam' ?'checked': ''}
            >
            <label class="form-check-label">
                Nam
            </label>
        </div>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Nu" name="gioiTinh"
            ${sinhVien.gioiTinh == 'Nu' ?'checked': ''}
            >
            <label class="form-check-label">
                Nu
            </label>
        </div>
    </div>
    <div class="mb-3">
        <label class="form-label">Trang thai</label>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Hoat dong" name="trangThai"
            ${sinhVien.trangThai == 'Hoat dong' ?'checked': ''}

            >
            <label class="form-check-label">
                Hoat dong
            </label>
        </div>
        <div class="form-check col-4">
            <input class="form-check-input" type="radio" value="Ngung hoat dong" name="trangThai"
            ${sinhVien.trangThai == 'Ngung hoat dong' ?'checked': ''}

            >
            <label class="form-check-label">
                Ngung hoat dong
            </label>
        </div>

    </div>

    <button type="submit" class="btn btn-primary">Submit</button>
</form>
</body>
</html>