from fastapi import FastAPI, Response
from pydantic import BaseModel
from diffusers import StableDiffusionPipeline
import torch
import io

# To run this python script do this: fastapi dev main.py
# Define request body model
class Prompt(BaseModel):
    prompt: str

app = FastAPI()

# Load model once at startup
print("Loading model... (first time may take a while)")
pipe = StableDiffusionPipeline.from_pretrained(
    "runwayml/stable-diffusion-v1-5",  # relatively fast model
    torch_dtype=torch.float32           # use float32 for CPU compatibility
)
pipe = pipe.to("cuda" if torch.cuda.is_available() else "cpu")
print("Model loaded!")

@app.post("/generate")
def generate_image(data: Prompt):
    prompt = data.prompt

    # Generate image
    image = pipe(prompt).images[0]

    # Convert to bytes
    img_bytes = io.BytesIO()
    image.save(img_bytes, format="PNG")
    img_bytes.seek(0)

    return Response(content=img_bytes.read(), media_type="image/png")
