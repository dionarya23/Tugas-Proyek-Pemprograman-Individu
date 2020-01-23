-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jan 09, 2020 at 05:39 AM
-- Server version: 10.1.37-MariaDB
-- PHP Version: 7.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `perpus_10517088`
--

-- --------------------------------------------------------

--
-- Table structure for table `anggota`
--

CREATE TABLE `anggota` (
  `NoAnggota` varchar(10) NOT NULL,
  `Nama` varchar(100) NOT NULL,
  `Alamat` varchar(300) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `anggota`
--

INSERT INTO `anggota` (`NoAnggota`, `Nama`, `Alamat`) VALUES
('A001', 'Risyaf', 'Bandung'),
('A002', 'Khansa', 'Jakarta'),
('A003', 'Mahira Dan', 'Bandung'),
('A004', 'Mufti', 'Bandung'),
('A005', 'Alfian D', 'Maluku'),
('A006', 'Dani A', 'Bandung'),
('A007', 'Shidqie', 'Jakarta'),
('A008', 'Dane', 'Surabaya'),
('A009', 'Danu', 'Bali'),
('A010', 'Nura', 'Bandung');

-- --------------------------------------------------------

--
-- Table structure for table `buku`
--

CREATE TABLE `buku` (
  `KodeBuku` varchar(100) NOT NULL,
  `Judul` varchar(200) NOT NULL,
  `Penulis` varchar(100) NOT NULL,
  `Penerbit` varchar(100) NOT NULL,
  `TahunTerbit` varchar(4) NOT NULL,
  `Status` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `buku`
--

INSERT INTO `buku` (`KodeBuku`, `Judul`, `Penulis`, `Penerbit`, `TahunTerbit`, `Status`) VALUES
('B001', 'TPP', 'Dann', 'UNIKOM', '2014', 'Dipinjam'),
('B002', 'TSIGD', 'Dana', 'UNIKOM', '2014', 'Dipinjam'),
('B003', 'Perangkat', 'Dani', 'UNIKOM', '2015', 'Dipinjam'),
('B004', 'PBD2', 'Dan', 'UNIKOM', '2019', 'Dipinjam'),
('B005', 'Sistem Lunak', 'Alfian', 'UNIKOM', '2017', 'Dipinjam'),
('B006', 'DGS', 'GL', 'ITHB', '2016', 'Dipinjam'),
('B007', 'TBD', 'Dan', 'UNIKOM', '2019', 'Ada'),
('B008', 'RPL', 'Ra', 'UNIKOM', '2017', 'Dipinjam'),
('B009', 'RPL Terstruktur', 'Rau', 'UNIKOM', '2019', 'Ada');

-- --------------------------------------------------------

--
-- Table structure for table `detailpinjam`
--

CREATE TABLE `detailpinjam` (
  `NoPinjam` varchar(5) NOT NULL,
  `KodeBuku` varchar(10) NOT NULL,
  `Status` varchar(15) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `detailpinjam`
--

INSERT INTO `detailpinjam` (`NoPinjam`, `KodeBuku`, `Status`) VALUES
('P001', 'B006', 'Dikembalikan'),
('P002', 'B003', 'Dikembalikan'),
('P003', 'B003', 'Dikembalikan'),
('P004', 'B004', 'Dikembalikan'),
('P006', 'B005', 'Dikembalikan'),
('P009', 'B005', 'Dikembalikan'),
('P010', 'B005', 'Dikembalikan');

-- --------------------------------------------------------

--
-- Table structure for table `kembalibuku`
--

CREATE TABLE `kembalibuku` (
  `NoPinjam` varchar(5) NOT NULL,
  `NoAnggota` varchar(10) NOT NULL,
  `TglPengembalian` date NOT NULL,
  `TglKembalikan` date NOT NULL,
  `Keterlambatan` varchar(100) NOT NULL,
  `Denda` varchar(25) NOT NULL,
  `Total` int(25) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `kembalibuku`
--

INSERT INTO `kembalibuku` (`NoPinjam`, `NoAnggota`, `TglPengembalian`, `TglKembalikan`, `Keterlambatan`, `Denda`, `Total`) VALUES
('P001', 'A001', '2020-01-01', '2020-01-08', '4', '4000', 4004),
('P002', 'A002', '2019-12-19', '2020-01-09', '351', '351000', 351351),
('P003', 'A002', '2019-12-19', '2020-01-09', '351', '351000', 351351),
('P004', 'A003', '2019-12-26', '2020-01-09', '344', '344000', 344344),
('P005', 'A006', '2019-12-26', '2020-01-09', '344', '344000', 344344),
('P006', 'A004', '2019-12-26', '2020-01-09', '344', '344000', 344344),
('P009', 'A004', '2019-12-26', '2020-01-09', '344', '344000', 344344);

-- --------------------------------------------------------

--
-- Table structure for table `pinjam`
--

CREATE TABLE `pinjam` (
  `NoPinjam` varchar(5) NOT NULL,
  `TglPinjam` date NOT NULL,
  `NoAnggota` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `pinjam`
--

INSERT INTO `pinjam` (`NoPinjam`, `TglPinjam`, `NoAnggota`) VALUES
('P001', '2020-01-01', 'A001'),
('P002', '2019-12-19', 'A002'),
('P003', '2019-12-19', 'A002'),
('P004', '2019-12-26', 'A003'),
('P005', '2019-12-26', 'A006'),
('P006', '2019-12-26', 'A004'),
('P009', '2019-12-26', 'A004'),
('P010', '2019-12-26', 'A004');

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `username` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `nama` varchar(100) NOT NULL,
  `bagian` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`username`, `password`, `nama`, `bagian`) VALUES
('001', '001', 'risya', 'admin'),
('002', '002', 'khansa', 'sirkulasi');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `anggota`
--
ALTER TABLE `anggota`
  ADD PRIMARY KEY (`NoAnggota`);

--
-- Indexes for table `buku`
--
ALTER TABLE `buku`
  ADD PRIMARY KEY (`KodeBuku`);

--
-- Indexes for table `detailpinjam`
--
ALTER TABLE `detailpinjam`
  ADD PRIMARY KEY (`NoPinjam`);

--
-- Indexes for table `kembalibuku`
--
ALTER TABLE `kembalibuku`
  ADD PRIMARY KEY (`NoPinjam`);

--
-- Indexes for table `pinjam`
--
ALTER TABLE `pinjam`
  ADD PRIMARY KEY (`NoPinjam`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`username`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
