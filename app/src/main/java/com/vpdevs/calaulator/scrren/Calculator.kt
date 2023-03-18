package com.vpdevs.calaulator.scrren

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vpdevs.calaulator.ui.theme.BackGround
import com.vpdevs.calaulator.ui.theme.ButtonBackground
import com.vpdevs.calaulator.ui.theme.ButtonOperationColor
import com.vpdevs.calaulator.ui.theme.TextGray

@Composable
fun Calculator() {
    val input = remember {
        mutableStateOf("")
    }
    val output = remember {
        mutableStateOf("0")
    }
    val variableA = remember {
        mutableStateOf("0")
    }
    val variableB = remember {
        mutableStateOf("0")
    }
    val operation = remember {
        mutableStateOf(Operation.NoOperation)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = BackGround
            )
    ) {
        Text(
            text = input.value,
            fontSize = 30.sp,
            textAlign = TextAlign.End,
            color = TextGray,
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .wrapContentHeight(Alignment.Bottom)
                .padding(end = 8.dp)
        )
        Text(
            text = output.value,
            fontSize = 40.sp,
            fontWeight = Bold,
            textAlign = TextAlign.End,
            color = TextGray,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(end = 8.dp, bottom = 4.dp)
        )
        OperationRowUI(variableA, input, output, operation)
        ShowNumPad(input, variableA, variableB, operation, output)
    }
}

@Composable
fun OperationRowUI(
    variableA: MutableState<String>,
    input: MutableState<String>,
    output: MutableState<String>,
    operationState: MutableState<Operation>
) {
    if (variableA.value.isEmpty()) {
        variableA.value = input.value
    }
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        val modifier = Modifier
            .weight(1f)
            .height(75.dp)
            .padding(2.dp)

        OperationsButtonUI(
            modifier,
            id = com.vpdevs.calaulator.R.drawable.ic_add,
            operation = Operation.Add,
            operationState = operationState,
            input = input,
            output = output
        )

        OperationsButtonUI(
            modifier,
            com.vpdevs.calaulator.R.drawable.ic_remove,
            operation = Operation.Subtract,
            operationState = operationState,
            input = input,
            output = output
        )

        OperationsButtonUI(
            modifier,
            com.vpdevs.calaulator.R.drawable.ic_multiply,
            operation = Operation.Multiply,
            operationState = operationState,
            input = input,
            output = output
        )

        OperationsButtonUI(
            modifier,
            com.vpdevs.calaulator.R.drawable.ic_divide,
            operation = Operation.Divide,
            operationState = operationState,
            input = input,
            output = output
        )

        OperationsButtonUI(
            modifier,
            com.vpdevs.calaulator.R.drawable.ic_zero,
            operation = Operation.Reset,
            operationState = operationState,
            input = input,
            output = output
        )

    }
}

enum class Operation {
    Add, Subtract, Multiply, Divide, NoOperation, Reset
}

@Composable
fun OperationsButtonUI(
    modifier: Modifier,
    @DrawableRes id: Int,
    operation: Operation,
    operationState: MutableState<Operation>,
    input: MutableState<String>,
    output: MutableState<String>,

    ) {
    val interactionSource = remember { MutableInteractionSource() }
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ButtonBackground,
        ), onClick = {
            operationState.value = operation

            if (input.value.isNotEmpty()) {
                if (operation.name == "Reset") {
                    input.value = "0"
                    output.value = "0"
                }else{
                    input.value = input.value +
                            getOperationString(operation = operation)
                }

            }

        }, modifier = modifier.indication(
            interactionSource, rememberRipple(
                radius = 4.dp, color = ButtonOperationColor
            )
        )
    ) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = "Icons",
            tint = ButtonOperationColor,
            modifier = Modifier.height(50.dp)
        )

    }
}

private fun getOperationString(operation: Operation): String {
    return when (operation.name) {
        Operation.Add.name -> "+"
        Operation.Subtract.name -> "-"
        Operation.Multiply.name -> "x"
        Operation.Divide.name -> "\u00F7"
        Operation.Reset.name -> "0"
        else -> {
            ""
        }
    }
}

private fun doCalculation(
    input: MutableState<String>, operation: Operation
): Float {

    val op = when (operation) {
        Operation.Add -> "+"
        Operation.Subtract -> "-"
        Operation.Multiply -> "x"
        Operation.Divide -> "\u00F7"
        else -> {
            "+"
        }
    }
    val a = input.value.split(op)[0].toFloat()
    val b = input.value.split(op)[1].toFloat()
    return when (operation) {
        Operation.Add -> a + b
        Operation.Subtract -> a - b
        Operation.Multiply -> a * b
        Operation.Divide -> a / b
        else -> {
            0.0f
        }
    }
}


private fun getNumPadDatList(): ArrayList<String> {
    val list = ArrayList<String>()
    for (it in 1..9) {
        list.add(it.toString())
    }
    list.add("0")
    list.add(".")
    list.add("=")
    return list
}

@Composable
fun ShowNumPad(
    input: MutableState<String>,
    variableA: MutableState<String>,
    variableB: MutableState<String>,
    operation: MutableState<Operation>,
    output: MutableState<String>
) {
    val list = getNumPadDatList()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        // content padding
        contentPadding = PaddingValues(
            2.dp
        ),
        content = {
            items(list.size) { index ->
                Card(
                    backgroundColor = ButtonBackground,
                    modifier = Modifier
                        .padding(2.dp)
                        .fillMaxWidth()
                        .clickable {

                            /* if (operation.value.name != "NoOperation") {
                                 input.value = input.value +
                             }*/
                            if (list[index] == "=") {
                                if (variableA.value.isNotEmpty() &&
                                    variableB.value.isNotEmpty()
                                ) {
                                    output.value = doCalculation(
                                        input,
                                        operation.value
                                    ).toString()
                                }
                            } else {
                                if (input.value.length != 5) {
                                    input.value = input.value + list[index]
                                }
                            }
                        },
                ) {
                    var color = Color.White
                    if (list[index] == "=") {
                        color = ButtonOperationColor
                    }
                    Text(
                        text = list[index],
                        fontSize = 30.sp,
                        color = color,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    Calculator()
}