from pyspark.sql import SparkSession
from pyspark.ml.classification import NaiveBayes
from pyspark.ml.evaluation import MulticlassClassificationEvaluator
from pyspark.ml.feature import VectorAssembler
from pyspark.mllib.evaluation import MulticlassMetrics
import os
os.environ["SPARK_HOME"] = "C:\spark-2.4.6-bin-hadoop2.7"
spark = SparkSession.builder.appName("ICP7").getOrCreate()
spark.sparkContext.setLogLevel("ERROR")
# Load data and select feature and label columns
data = spark.read.format("csv").option("header", True).option("inferSchema", True).option("delimiter", ",").load(
    "C:/Users/anees/Downloads/BDP/adult.data")
data = data.withColumnRenamed("age", "label").select("label", "education-num", "hours-per-week")
data = data.select(data.label.cast("double"), "education-num", "hours-per-week")
# Create vector assembler for feature columns
assembler = VectorAssembler(inputCols=data.columns[1:], outputCol="features")
data = assembler.transform(data)
data.show()
# Split data into training and test data set
training, test = data.select("label", "features").randomSplit([0.85, 0.15])
# Create Navie Bayes model and fit the model with training dataset
nb = NaiveBayes()
model = nb.fit(training)
# Generate prediction from test dataset
predictions = model.transform(test)
# Evaluate the accuracy of the model
evaluator = MulticlassClassificationEvaluator()
accuracy = evaluator.evaluate(predictions)
# Show model accuracy
print("Accuracy:", accuracy)
# Report
predictionAndLabels = predictions.select("label", "prediction").rdd
metrics = MulticlassMetrics(predictionAndLabels)
print("Confusion Matrix:", metrics.confusionMatrix())
print("Precision:", metrics.precision())
print("Recall:", metrics.recall())
print("F-measure:", metrics.fMeasure())