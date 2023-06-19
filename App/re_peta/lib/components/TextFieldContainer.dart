import 'package:flutter/material.dart';
import '../colors.dart';

class TextFieldContainer extends StatelessWidget {
  const TextFieldContainer(
      {Key? key,
      required this.placeholder,
      required this.margin,
      required this.controller})
      : super(key: key);

  final String placeholder;
  final double margin;
  final TextEditingController controller;

  @override
  Widget build(BuildContext context) {
    Size size = MediaQuery.of(context).size;

    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(10),
        color: lightColor,
      ),
      width: size.width * 0.8,
      height: 60,
      margin: EdgeInsets.only(top: margin),
      child: TextField(
        controller: controller,
        textAlignVertical: TextAlignVertical.center,
        decoration: InputDecoration(
          border: InputBorder.none,
          hintText: placeholder,
          contentPadding: const EdgeInsets.only(left: 20, right: 20, bottom: 5),
        ),
        style: const TextStyle(fontSize: 20),
      ),
    );
  }
}
