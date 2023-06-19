import 'package:flutter/material.dart';
import '../colors.dart';

class ListItem extends StatelessWidget {
  const ListItem({
    super.key,
    required this.item,
  });

  final String item;

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: BoxDecoration(
        borderRadius: BorderRadius.circular(80),
        color: blanco,
        boxShadow: const [
          BoxShadow(
              color: blackCat,
              spreadRadius: -10,
              blurRadius: 12,
              offset: Offset(0, 4),
              blurStyle: BlurStyle.normal),
        ],
      ),
      height: 70,
      margin: const EdgeInsets.only(top: 25, left: 20, right: 20),
      child: Container(
        alignment: Alignment.centerLeft,
        margin: const EdgeInsets.only(left: 20, right: 20),
        child: Row(
          children: [
            Expanded(
              child: Text(
                item,
                textAlign: TextAlign.left,
                style: const TextStyle(
                    fontWeight: FontWeight.bold,
                    color: blueocean,
                    fontSize: 16),
                maxLines: 1,
                overflow: TextOverflow.ellipsis,
              ),
            ),
            const Icon(Icons.arrow_circle_right_outlined,
                color: blueocean, size: 35),
          ],
        ),
      ),
    );
  }
}
